import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.AgentTool;
import com.google.adk.tools.BaseTool;
import com.google.adk.tools.GoogleSearchTool;
import com.google.adk.tools.mcp.McpToolset;
import com.google.adk.tools.mcp.SseServerParameters;
// import ToolContext
import  com.google.adk.tools.ToolContext;
import com.google.adk.tools.Annotations.Schema;
import com.google.adk.tools.FunctionTool;
//import com.google.adk.tools.annotations.FunctionTool;
// import Map
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MyAgentApp {

    private static final Logger logger = Logger.getLogger(MyAgentApp.class.getName());

    // --- Define Constants ---
    private static final String MODEL_NAME = "gemini-2.0-flash";

  /**
   * Retrieves the current weather report for a specified city.
   *
   * @param city The city for which to retrieve the weather report.
   * @param toolContext The context for the tool.
   * @return A dictionary containing the weather information.
   */
  public static Map<String, Object> getWeatherReport(
      @Schema(name = "city")
      String city,
      @Schema(name = "toolContext")
      ToolContext toolContext) {
    Map<String, Object> response = new HashMap<>();

    if (city.toLowerCase(Locale.ROOT).equals("geneva")) {
      response.put("status", "success");
      response.put(
          "report",
          String.format("The current weather in %s is cloudy with a temperature of 18 degrees Celsius and a chance of rain.", city));
    } else if (city.toLowerCase(Locale.ROOT).equals("basel")) {
      response.put("status", "success");
      response.put(
          "report", String.format("The weather in %s is sunny with a temperature of 25 degrees Celsius.", city));
    } else {
      response.put("status", "error");
      response.put(
          "error_message", String.format("Weather information for '%s' is not available.", city));
    }
    return response;
  }

    // ROOT_AGENT needed for ADK Web UI. 
    public static BaseAgent ROOT_AGENT = initAgent();

    public static BaseAgent initAgent() {
        // Set up MCP Toolbox connection to Cloud SQL 
        try {
        String mcpAirBnBServerUrl = "http://localhost:8090/sse";
        SseServerParameters paramsAirBnB = SseServerParameters.builder().url(mcpAirBnBServerUrl).build();
        McpToolset.McpToolsAndToolsetResult toolsAndToolsetResult = McpToolset.fromServer(paramsAirBnB, new ObjectMapper()).get();

        List<BaseTool> toolAirBnBList = toolsAndToolsetResult.getTools().stream().map(mcpTool -> (BaseTool) mcpTool)
                .collect(Collectors.toList());
        logger.info("🛠️ AIRBNB MCP TOOLS: ");
        toolAirBnBList.forEach(tool -> {
            logger.info("🛠️ AIRBNB MCP Tool: " + tool.name() + " - " + tool.description());
        });
        if (toolAirBnBList.isEmpty()) {
                logger.warning("⚠️ No MCP tools found. Please check your MCP Toolbox setup.");
         }
            String mcpServerUrl = System.getenv("MCP_TOOLBOX_URL");
            if (mcpServerUrl == null || mcpServerUrl.isEmpty()) {
                mcpServerUrl = "http://127.0.0.1:5000/mcp/"; // Fallback URL
                logger.warning("⚠️ MCP_TOOLBOX_URL environment variable not set, using default:" + mcpServerUrl);
            }

            SseServerParameters params = SseServerParameters.builder().url(mcpServerUrl).build();
            logger.info("🕰️ Initializing MCP toolset with params" + params);

            McpToolset.McpToolsAndToolsetResult result = McpToolset.fromServer(params, new ObjectMapper()).get();
            logger.info("⭐ MCP Toolset initialized: " + result.toString());
            if (result.getTools() != null && !result.getTools().isEmpty()) {
                logger.info("⭐ MCP Tools loaded: " + result.getTools().size());
            }
            List<BaseTool> mcpTools = result.getTools().stream()
                    .map(mcpTool -> (BaseTool) mcpTool)
                    .collect(Collectors.toList());
            logger.info("🛠️ MCP TOOLS: "+mcpTools.toString());
            if (mcpTools.isEmpty()) {
                logger.warning("⚠️ No MCP tools found. Please check your MCP Toolbox setup.");
            }
            FunctionTool weatherTool =
                FunctionTool.create(
                    MyAgentApp.class.getMethod(
                "getWeatherReport", String.class, ToolContext.class));
            logger.info("🌤️ Weather Tool: " + weatherTool.toString());
/*
            // Add Function Tool for weather report
            BaseTool weatherFromBaseTool = BaseTool.builder()
                    .name("get_weather_report")
                    .description("Retrieves the current weather report for a specified city.")
                    .inputSchema("""
                            {
                              "type": "object",
                              "properties": {
                                "city": {               
                                  "type": "string",
                                  "description": "The city for which to retrieve the weather report."
                                }
                              },
                              "required": ["city"]
                            }
                            """)
                    .outputSchema("""
                            {
                              "type": "object",
                              "properties": {
                                "status": {
                                  "type": "string",
                                  "description": "The status of the weather report request."
                                },
                                "report": {
                                  "type": "string",
                                  "description": "The weather report for the specified city."
                                },
                                "error_message": {
                                  "type": "string",
                                  "description": "An error message if the weather report could not be retrieved."
                                }
                              },
                              "required": ["status"]
                            }
                            """)
                    .function((params, toolContext) -> {
                        String city = (String) params.get("city");
                        return getWeatherReport(city, toolContext);
                    })
                    .build();
            logger.info("🌤️ Weather Tool: " + weatherFromBaseTool.toString());
            */
            // Add GoogleSearch tool - Workaround for https://github.com/google/adk-python/issues/134 
            LlmAgent googleSearchAgent = LlmAgent.builder()
                    .model("gemini-2.0-flash")
                    .name("google_search_agent")
                    .description("Search Google Search")
                    .instruction("""
                You're a specialist in Google Search
                """)
                    .tools(new GoogleSearchTool()) // Your Google search tool
                    .outputKey("google_search_result")
                    .build();
            AgentTool searchTool = AgentTool.create(googleSearchAgent, false);
            List<BaseTool> allTools = new ArrayList<>(mcpTools);
            allTools.add(toolAirBnBList.get(0)); // Assuming the first tool is the AirBnB tool
            allTools.add(searchTool);
            allTools.add(weatherTool);
            //allTools.add(weatherFromBaseTool);
            logger.info("🌈 ALL TOOLS: " + allTools.toString());
            return LlmAgent.builder()
                    .model(MODEL_NAME)
                    .name("MyAgent")
                    .description("Helps for City Weather and Hotel Search")
                    .instruction(
                            """
                            You are a skilled expert in customer Support for City Hotel Search also as AirBnB assets and associated Weather Report.

                            Your general process is as follows:

                            1. **Understand the user's request.** Analyze the user's initial request to understand the goal - for example, "I plan to visit Basel City, is there any Hotel and what will be the next days weather ?" If you do not understand the request, ask for more information.   
                            2. **Identify the appropriate tools.** You will be provided with tools for a SQL-based hotel database (create, update, search hotels by description). You will also be able to web search via Google Search. Identify one **or more** appropriate tools to accomplish the user's request.  
                            3. **Populate and validate the parameters.** Before calling the tools, do some reasoning to make sure that you are populating the tool parameters correctly. For example, when searching for hotel, and then propose assoiated weather for the user request city.   
                            4. **Call the tools.** Once the parameters are validated, call the tool with the determined parameters.  
                            5. **Analyze the tools' results, and provide insights back to the user.** Return the tools' result in a human-readable format. State which tools you called, if any. If your result is 2 or more resultass, always use a markdown table to report back. If there is any code, or timestamp, in the result, format the code with markdown backticks, or codeblocks.   
                            6. **Ask the user either to book either to search for AirBnB assets about the city.**  
                            6. **Ask the user if they need anything else.**  
                """)
                    .tools(allTools)
                    .outputKey("hotel_city_result")
                    .build();
        } catch (Exception e) {
            logger.info("Error initializing MCP toolset and starting agent " + e.getMessage());
            return null;
        }
    }
}

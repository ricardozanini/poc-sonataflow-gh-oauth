package org.acme.poc.sonataflow.consent.web;

import org.acme.poc.sonataflow.consent.web.client.WorkflowClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Blocking
@Path("/exec_workflow")
public class ExecWorkflow {


    @Inject
    Template exec_workflow;

    @RestClient
    WorkflowClient workflowClient;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@QueryParam("access_token") String accessToken) throws JsonProcessingException {
        try (Response response = workflowClient.execWorkflow("Bearer " + accessToken, "{}")) {
            String rawJson = response.readEntity(String.class);

            // Parse and re-serialize as pretty-printed JSON
            ObjectMapper mapper = new ObjectMapper();
            Object parsed = mapper.readValue(rawJson, Object.class); // parse as generic object
            String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(parsed);

            // Pass the pretty JSON to Qute
            return exec_workflow.data("workflow_response", prettyJson);
        }
    }

}

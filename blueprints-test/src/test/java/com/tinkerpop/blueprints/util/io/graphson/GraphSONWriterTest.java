package com.tinkerpop.blueprints.util.io.graphson;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jettison.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class GraphSONWriterTest {

    @Test
    public void outputGraphNoEmbeddedTypes() throws JSONException, IOException {
        Graph g = TinkerGraphFactory.createTinkerGraph();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        GraphSONWriter writer = new GraphSONWriter(g);
        writer.outputGraph(stream, null, null, GraphSONMode.NORMAL);

        stream.flush();
        stream.close();

        String jsonString = new String(stream.toByteArray());

        ObjectMapper m = new ObjectMapper();
        JsonNode rootNode = m.readValue(jsonString, JsonNode.class);

        // ensure that the JSON conforms to basic structure and that the right
        // number of graph elements are present.  other tests already cover element formatting
        Assert.assertNotNull(rootNode);

        Assert.assertTrue(rootNode.has(GraphSONTokens.MODE));
        Assert.assertEquals("NORMAL", rootNode.get(GraphSONTokens.MODE).getTextValue());

        Assert.assertTrue(rootNode.has(GraphSONTokens.VERTICES));

        ArrayNode vertices = (ArrayNode) rootNode.get(GraphSONTokens.VERTICES);
        Assert.assertEquals(6, vertices.size());

        Assert.assertTrue(rootNode.has(GraphSONTokens.EDGES));

        ArrayNode edges = (ArrayNode) rootNode.get(GraphSONTokens.EDGES);
        Assert.assertEquals(6, edges.size());
    }

    @Test
    public void outputGraphWithEmbeddedTypes() throws JSONException, IOException {
        Graph g = TinkerGraphFactory.createTinkerGraph();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        GraphSONWriter writer = new GraphSONWriter(g);
        writer.outputGraph(stream, null, null, GraphSONMode.EXTENDED);

        stream.flush();
        stream.close();

        String jsonString = new String(stream.toByteArray());

        ObjectMapper m = new ObjectMapper();
        JsonNode rootNode = m.readValue(jsonString, JsonNode.class);

        // ensure that the JSON conforms to basic structure and that the right
        // number of graph elements are present.  other tests already cover element formatting
        Assert.assertNotNull(rootNode);
        Assert.assertTrue(rootNode.has(GraphSONTokens.MODE));
        Assert.assertEquals("EXTENDED", rootNode.get(GraphSONTokens.MODE).getTextValue());

        Assert.assertTrue(rootNode.has(GraphSONTokens.VERTICES));

        ArrayNode vertices = (ArrayNode) rootNode.get(GraphSONTokens.VERTICES);
        Assert.assertEquals(6, vertices.size());

        Assert.assertTrue(rootNode.has(GraphSONTokens.EDGES));

        ArrayNode edges = (ArrayNode) rootNode.get(GraphSONTokens.EDGES);
        Assert.assertEquals(6, edges.size());
    }

    @Test
    public void outputGraphWithCompact() throws JSONException, IOException {
        Graph g = TinkerGraphFactory.createTinkerGraph();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        GraphSONWriter writer = new GraphSONWriter(g);
        writer.outputGraph(stream, null, null, GraphSONMode.COMPACT);

        stream.flush();
        stream.close();

        String jsonString = new String(stream.toByteArray());

        ObjectMapper m = new ObjectMapper();
        JsonNode rootNode = m.readValue(jsonString, JsonNode.class);

        // ensure that the JSON conforms to basic structure and that the right
        // number of graph elements are present.  other tests already cover element formatting
        Assert.assertNotNull(rootNode);
        Assert.assertTrue(rootNode.has(GraphSONTokens.MODE));
        Assert.assertEquals("COMPACT", rootNode.get(GraphSONTokens.MODE).getTextValue());

        Assert.assertTrue(rootNode.has(GraphSONTokens.VERTICES));

        ArrayNode vertices = (ArrayNode) rootNode.get(GraphSONTokens.VERTICES);
        Assert.assertEquals(6, vertices.size());

        Assert.assertTrue(rootNode.has(GraphSONTokens.EDGES));

        ArrayNode edges = (ArrayNode) rootNode.get(GraphSONTokens.EDGES);
        Assert.assertEquals(6, edges.size());
    }
}

package edu.ithaca.dragon.tecmap.suggester;

import edu.ithaca.dragon.tecmap.Settings;
import edu.ithaca.dragon.tecmap.conceptgraph.*;
import edu.ithaca.dragon.tecmap.io.reader.CSVReader;
import edu.ithaca.dragon.tecmap.io.record.ConceptGraphRecord;
import edu.ithaca.dragon.tecmap.io.record.LearningResourceRecord;
import edu.ithaca.dragon.tecmap.io.reader.SakaiReader;
import edu.ithaca.dragon.tecmap.learningobject.ExampleLearningObjectLinkRecordFactory;
import edu.ithaca.dragon.tecmap.learningobject.ExampleLearningObjectResponseFactory;
import edu.ithaca.dragon.tecmap.learningobject.LearningObjectResponse;
import edu.ithaca.dragon.tecmap.learningobject.LearningResource;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by home on 5/20/17.
 */
public class LearningObjectSuggesterTest {

    @Test
    public void suggestionResourceSimpleTest() {
        ConceptGraph orig = ExampleConceptGraphFactory.simpleTestGraphTest();

        List<ConceptNode> concepts = LearningObjectSuggester.conceptsToWorkOn(orig);
        SuggestionResource res = new SuggestionResource(orig, concepts);
        List<LearningObjectSuggestion> incomTest = res.incompleteList;
        List<LearningObjectSuggestion> wrongTest = res.wrongList;

        Assert.assertEquals(wrongTest.size(), 3);
        Assert.assertEquals(wrongTest.get(0).getId(), "Q3");
        Assert.assertEquals(wrongTest.get(1).getId(), "Q4");
        Assert.assertEquals(wrongTest.get(2).getId(), "Q5");

        Assert.assertEquals(incomTest.size(), 0);
    }

    @Test
    public void suggestionResourceMediumTest() {
        ConceptGraph orig = ExampleConceptGraphFactory.willExampleConceptGraphTestOneStudent();

        List<ConceptNode> concepts = LearningObjectSuggester.conceptsToWorkOn(orig);
        SuggestionResource res = new SuggestionResource(orig, concepts);

        List<LearningObjectSuggestion> incomTest = res.incompleteList;
        List<LearningObjectSuggestion> wrongTest = res.wrongList;

        Assert.assertEquals(incomTest.size(), 3);
        Assert.assertEquals(incomTest.get(0).getId(), "Q6");
        Assert.assertEquals(incomTest.get(1).getId(), "Q13");
        Assert.assertEquals(incomTest.get(2).getId(), "Q10");

        Assert.assertEquals(wrongTest.size(), 4);
        Assert.assertEquals(wrongTest.get(0).getId(), "Q7");
        Assert.assertEquals(wrongTest.get(1).getId(), "Q15");
        Assert.assertEquals(wrongTest.get(2).getId(), "Q9");
        Assert.assertEquals(wrongTest.get(3).getId(), "Q14");

    }

    @Test
    public void conceptsToWorkOnTest() {
        ConceptGraph orig = ExampleConceptGraphFactory.willExampleConceptGraphTestOneStudent();

        List<ConceptNode> concepts = LearningObjectSuggester.conceptsToWorkOn(orig);

        Assert.assertEquals(concepts.size(), 2);

        Assert.assertEquals(concepts.get(0).getID(), "Boolean");
        Assert.assertEquals(concepts.get(1).getID(), "Counting");

    }

    @Test
    public void RealDataConceptsTOWorkOn() throws IOException {

        CohortConceptGraphs cohortConceptGraphs = null;

        //create the graph structure to be copied for each user
        ConceptGraphRecord structureRecord = ConceptGraphRecord.buildFromJson(Settings.TEST_RESOURCE_DIR + "ManuallyCreated/comp220GraphExample.json");
        List<LearningResourceRecord> linkRecord = LearningResourceRecord.buildListFromJson(Settings.TEST_RESOURCE_DIR + "ManuallyCreated/comp220Resources.json");
        ConceptGraph graph = new ConceptGraph(structureRecord, linkRecord);

        //create the data to be used to create and populate the graph copies
        CSVReader csvReader = new SakaiReader(Settings.TEST_RESOURCE_DIR + "ManuallyCreated/exampleDataAssessment.csv");
        List<LearningObjectResponse> assessments = csvReader.getManualGradedResponses();

        //create the average and individual graphs
        cohortConceptGraphs = new CohortConceptGraphs(graph, assessments);

        ConceptGraph userGraph = cohortConceptGraphs.getUserGraph("s04");
        List<ConceptNode> concepts = LearningObjectSuggester.conceptsToWorkOn(userGraph);

        Assert.assertEquals(concepts.size(), 3);
        Assert.assertEquals(concepts.get(0).getID(), "Recursion");
        Assert.assertEquals(concepts.get(1).getID(), "Pointers");
        Assert.assertEquals(concepts.get(2).getID(), "List");

    }


    @Test
    public void RealDataConceptsTOWorkOnZeroSugg() throws IOException {

        CohortConceptGraphs cohortConceptGraphs = null;

        //create the graph structure to be copied for each user
        ConceptGraphRecord structureRecord = ConceptGraphRecord.buildFromJson(Settings.TEST_RESOURCE_DIR + "ManuallyCreated/comp220GraphExample.json");
        List<LearningResourceRecord> linkRecord = LearningResourceRecord.buildListFromJson(Settings.TEST_RESOURCE_DIR + "ManuallyCreated/comp220Resources.json");
        ConceptGraph graph = new ConceptGraph(structureRecord, linkRecord);

        //create the data to be used to create and populate the graph copies
        CSVReader csvReader = new SakaiReader(Settings.TEST_RESOURCE_DIR + "ManuallyCreated/exampleDataAssessment.csv");
        List<LearningObjectResponse> assessments = csvReader.getManualGradedResponses();

        //create the average and individual graphs
        cohortConceptGraphs = new CohortConceptGraphs(graph, assessments);

        ConceptGraph userGraph = cohortConceptGraphs.getUserGraph("s03");
        List<ConceptNode> concepts = LearningObjectSuggester.conceptsToWorkOn(userGraph);
        Assert.assertEquals(concepts.size(), 0);




        ConceptGraph userGraph2 = cohortConceptGraphs.getUserGraph("s02");
        List<ConceptNode> concepts2 = LearningObjectSuggester.conceptsToWorkOn(userGraph2);
        Assert.assertEquals(concepts2.size(), 3);
        Assert.assertEquals(concepts2.get(0).getID(), "Recursion");
        Assert.assertEquals(concepts2.get(1).getID(), "Pointers");
        Assert.assertEquals(concepts2.get(2).getID(), "List");

    }



    @Test
    public void buildSuggestionMapSimpleTest() {
        ConceptGraph orig = ExampleConceptGraphFactory.simpleTestGraphTest();
        //what it is

        List<ConceptNode> concepts = LearningObjectSuggester.conceptsToWorkOn(orig);
        HashMap<String, List<LearningObjectSuggestion>> objectSuggestionMap = LearningObjectSuggester.buildSuggestionMap(concepts, 1, orig);

        //what it should be
        List<LearningObjectSuggestion> testList3 = new ArrayList<>();
        Assert.assertEquals(1, objectSuggestionMap.size());
        Assert.assertEquals(testList3, objectSuggestionMap.get("C"));

    }

    @Test
    public void buildSuggestionMapWillOneStudentTest() {
        ConceptGraph orig = ExampleConceptGraphFactory.willExampleConceptGraphTestOneStudent();

        List<ConceptNode> concepts = LearningObjectSuggester.conceptsToWorkOn(orig);
        HashMap<String, List<LearningObjectSuggestion>> objectSuggestionMap = LearningObjectSuggester.buildSuggestionMap(concepts, 1, orig);

        Assert.assertEquals(2, objectSuggestionMap.size());

        Assert.assertEquals(objectSuggestionMap.get("Boolean").get(0).getId(), "Q6");
        Assert.assertEquals(objectSuggestionMap.get("Boolean").get(1).getId(), "Q10");
        Assert.assertEquals(objectSuggestionMap.get("Counting").get(0).getId(), "Q13");

    }

    @Test
    public void suggestedOrderBuildLearningObjectListTest() {
        List<LearningResourceRecord> myList = ExampleLearningObjectLinkRecordFactory.makeSimpleLOLRecords();
        myList.add(new LearningResourceRecord("Q10", Arrays.asList(LearningResource.Type.ASSESSMENT, LearningResource.Type.PRACTICE), Arrays.asList("A"), 1, 1));
        ConceptGraph orig = new ConceptGraph(ExampleConceptGraphRecordFactory.makeSimple(),
                myList, ExampleLearningObjectResponseFactory.makeSimpleResponses());

//        //from A nodes
        HashMap<String, Integer> testCompareA = new HashMap<String, Integer>();
        testCompareA.put("Q3", 2);
        testCompareA.put("Q4", 2);
        testCompareA.put("Q5", 2);
        testCompareA.put("Q6", 2);
        testCompareA.put("Q1", 1);
        testCompareA.put("Q2", 1);
        testCompareA.put("Q10", 1);

        HashMap<String, Integer> learningSummaryFromA = orig.buildLearningObjectSummaryList("A");
        HashMap<String, Integer> linkMap = orig.buildDirectConceptLinkCount();


        //makes sure that buildLearningObjectSummaryList works
        Assert.assertEquals(testCompareA, learningSummaryFromA);

        //build the suggested learning object list
        List<LearningObjectSuggestion> suggestedList = LearningObjectSuggester.buildLearningObjectSuggestionList(learningSummaryFromA, orig.getLearningObjectMap(), "A", linkMap);


        //this is ordered based on "level"
        List<LearningObjectSuggestion> suggestListTest = new ArrayList<>();
        suggestListTest.add(new LearningObjectSuggestion("Q1", 1, LearningObjectSuggestion.Level.RIGHT, "A", 1));
        suggestListTest.add(new LearningObjectSuggestion("Q2", 1, LearningObjectSuggestion.Level.RIGHT, "A", 1));
        suggestListTest.add(new LearningObjectSuggestion("Q3", 2, LearningObjectSuggestion.Level.WRONG, "A", 1));
        suggestListTest.add(new LearningObjectSuggestion("Q4", 2, LearningObjectSuggestion.Level.WRONG, "A", 1));
        suggestListTest.add(new LearningObjectSuggestion("Q5", 2, LearningObjectSuggestion.Level.WRONG, "A", 1));
        suggestListTest.add(new LearningObjectSuggestion("Q6", 2, LearningObjectSuggestion.Level.WRONG, "A", 1));
        suggestListTest.add(new LearningObjectSuggestion("Q10", 1, LearningObjectSuggestion.Level.INCOMPLETE, "A", 1));

        //orders the list based off of "level"
        LearningObjectSuggester.sortSuggestions(suggestedList);

        for (int i = 0; i < suggestedList.size(); i++) {

            Assert.assertEquals(suggestedList.get(i).getId(), suggestListTest.get(i).getId());
            Assert.assertEquals(suggestedList.get(i).getPathNum(), suggestListTest.get(i).getPathNum());
            Assert.assertEquals(suggestedList.get(i).getLevel(), suggestListTest.get(i).getLevel());
            Assert.assertEquals(suggestedList.get(i).getReasoning(), suggestListTest.get(i).getReasoning());

        }
    }


    @Test
    public void toStringTest() {
        ConceptGraph orig = ExampleConceptGraphFactory.willExampleConceptGraphTestOneStudent();

        List<ConceptNode> concepts = LearningObjectSuggester.conceptsToWorkOn(orig);
        SuggestionResource res = new SuggestionResource(orig, concepts);


        String incomString = res.toString(0);

        String wrongString = res.toString(1);


        Assert.assertEquals(incomString, "Resource: Q6\t Concepts it relates to: Boolean\t Importance: 1\t Direct Concept Links: 1" +
                "\nResource: Q13\t Concepts it relates to: Counting\t Importance: 1\t Direct Concept Links: 1"+
                "\nResource: Q10\t Concepts it relates to: Boolean\t Importance: 1\t Direct Concept Links: 4\n");


        Assert.assertEquals(wrongString, "Resource: Q7\t Concepts it relates to: Boolean\t Importance: 1\t Direct Concept Links: 1" +
                "\nResource: Q15\t Concepts it relates to: Counting\t Importance: 1\t Direct Concept Links: 1"+
                "\nResource: Q9\t Concepts it relates to: Boolean\t Importance: 1\t Direct Concept Links: 3"+
                "\nResource: Q14\t Concepts it relates to: Counting\t Importance: 1\t Direct Concept Links: 1\n");

    }


    @Test
    public void toStringTest2()throws IOException{
        CohortConceptGraphs cohortConceptGraphs = null;

        //create the graph structure to be copied for each user
        ConceptGraphRecord structureRecord = ConceptGraphRecord.buildFromJson(Settings.TEST_RESOURCE_DIR + "ManuallyCreated/comp220GraphExample.json");
        List<LearningResourceRecord> linkRecord = LearningResourceRecord.buildListFromJson(Settings.TEST_RESOURCE_DIR + "ManuallyCreated/comp220Resources.json");
        ConceptGraph graph = new ConceptGraph(structureRecord, linkRecord);

        //create the data to be used to create and populate the graph copies
        CSVReader csvReader = new SakaiReader(Settings.TEST_RESOURCE_DIR + "ManuallyCreated/exampleDataAssessment.csv");
        List<LearningObjectResponse> assessments = csvReader.getManualGradedResponses();

        //create the average and individual graphs
        cohortConceptGraphs = new CohortConceptGraphs(graph, assessments);

        ConceptGraph userGraph = cohortConceptGraphs.getUserGraph("s01");

        List<ConceptNode> concepts = LearningObjectSuggester.conceptsToWorkOn(userGraph);


        SuggestionResource res = new SuggestionResource(userGraph, concepts);


        String incomString = res.toString(0);

        String wrongString = res.toString(1);


        Assert.assertEquals(incomString, "");

        Assert.assertEquals(wrongString,"Resource: Lab 3: Comparing Array Library Efficiency\t Concepts it relates to: Abstract Data Types & Array\t Importance: 5\t Direct Concept Links: 3" +
                "\nResource: Lab 5: Comparing Searches\t Concepts it relates to: Recursion\t Importance: 1\t Direct Concept Links: 3"+
                "\nResource: Lab 8: Comparing Arrays and Linked Lists\t Concepts it relates to: Abstract Data Types & Array\t Importance: 1\t Direct Concept Links: 2"+
                "\nResource: Lab 6: ArrayList and Testing\t Concepts it relates to: Abstract Data Types & Array\t Importance: 1\t Direct Concept Links: 2" +
                "\nResource: Lab 7: Linked List\t Concepts it relates to: Abstract Data Types\t Importance: 1\t Direct Concept Links: 2\n");

    }
}
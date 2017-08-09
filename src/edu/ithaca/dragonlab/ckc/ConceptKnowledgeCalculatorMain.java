package edu.ithaca.dragonlab.ckc;

import edu.ithaca.dragonlab.ckc.ui.ConsoleUI;

public class ConceptKnowledgeCalculatorMain {

    public static void main(String[] args) {
//        ConceptKnowledgeCalculator.Mode startMode = ConceptKnowledgeCalculator.Mode.STRUCTUREGRAPH;
        ConceptKnowledgeCalculator.Mode startMode = ConceptKnowledgeCalculator.Mode.COHORTGRAPH;


        if (startMode == ConceptKnowledgeCalculator.Mode.STRUCTUREGRAPH) {
            new ConsoleUI("resources/comp220/comp220Graph.json");
        } else if (startMode == ConceptKnowledgeCalculator.Mode.COHORTGRAPH) {

//            new ConsoleUI("test/testresources/ManuallyCreated/basicRealisticConceptGraph.json",
//                    "test/testresources/ManuallyCreated/basicRealisticResource.json",
//                    "test/testresources/ManuallyCreated/basicRealisticAssessment.csv");


//                        new ConsoleUI("test/testresources/ManuallyCreated/simpleConceptGraph.json",
//                    "test/testresources/ManuallyCreated/simpleResource.json",
//                    "test/testresources/ManuallyCreated/simpleAssessmentMoreUsers.csv");



//            new ConsoleUI("resources/comp220/comp220Graph.json",
//                    "resources/comp220/comp220Resources.json",
//                    "localresources/comp220/comp220ExampleDataPortionCleaned.csv");

//            new ConsoleUI("resources/comp220/comp220Graph.json",
//                    "resources/comp220/comp220Resources.json",
//                    "localresources/comp220/comp220ExampleDataPortion.csv");

//
//            new ConsoleUI("test/testresources/ManuallyCreated/researchConceptGraph.json",
//                    "test/testresources/ManuallyCreated/researchResource1.json",
//                    "test/testresources/ManuallyCreated/researchAssessment1.csv");

//            new ConsoleUI("test/testresources/ManuallyCreated/researchConceptGraph.json",
//                    "test/testresources/ManuallyCreated/researchResource2.json",
//                    "test/testresources/ManuallyCreated/researchAssessment2.csv");


            new ConsoleUI("resources/comp220/comp220Graph.json",
                    "resources/comp220/comp220Resources.json",
                    "localresources/comp220/comp220ExampleDataPortionCleaned.csv");


        } else {
            throw new RuntimeException("Unrecognized starting mode, program cannot execute");
        }
    }
}

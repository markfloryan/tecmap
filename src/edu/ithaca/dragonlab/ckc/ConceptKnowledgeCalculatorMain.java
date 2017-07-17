package edu.ithaca.dragonlab.ckc;

import edu.ithaca.dragonlab.ckc.ui.ConsoleUI;

public class ConceptKnowledgeCalculatorMain {

    public static void main(String[] args) {
//        ConceptKnowledgeCalculator.Mode startMode = ConceptKnowledgeCalculator.Mode.STRUCTUREGRAPH;
        ConceptKnowledgeCalculator.Mode startMode = ConceptKnowledgeCalculator.Mode.COHORTGRAPH;


        if (startMode == ConceptKnowledgeCalculator.Mode.STRUCTUREGRAPH) {
            new ConsoleUI("resources/comp220/comp220Graph.json");
        } else if (startMode == ConceptKnowledgeCalculator.Mode.COHORTGRAPH) {
            new ConsoleUI("test/testresources/ManuallyCreated/basicRealisticConceptGraph.json",
                    "test/testresources/ManuallyCreated/basicRealisticResource.json",
                    "test/testresources/ManuallyCreated/basicRealisticAssessment.csv");


//                        new ConsoleUI("test/testresources/ManuallyCreated/researchConceptGraph.json",
//                    "test/testresources/ManuallyCreated/researchResource1.json",
//                    "test/testresources/ManuallyCreated/researchAssessment1.csv");

//            new ConsoleUI("resources/comp220/comp220Graph.json",
//                    "resources/comp220/comp220Resources.json",
//                    "localresources/comp220/comp220ExampleDataPortion.csv");
        } else {
            throw new RuntimeException("Unrecognized starting mode, program cannot execute");
        }
    }
}

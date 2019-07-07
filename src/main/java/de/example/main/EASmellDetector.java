package de.example.main;

import de.example.smells.*;

import java.util.ArrayList;
import java.util.List;

public class EASmellDetector {
    public static void main(String[] args) {
        ModelAdapter model = new ModelAdapter(args[0], args.length == 2 ? args[1] : null);
        Detector.setModel(model);

        List<Detector> detectors = new ArrayList<>();
        detectors.add(new CyclicDependency());
        detectors.add(new DeadComponent());
        detectors.add(new DenseStructure());
        detectors.add(new Documentation());
        detectors.add(new Duplication());
        detectors.add(new HubLikeModularization());
        detectors.add(new SharedPersistency());
        detectors.add(new StrictLayersViolation());
        detectors.add(new WeakenedModularity());

        System.out.print("\n");
        long startTotalTime = System.nanoTime();
        for (Detector detector : detectors) {
            System.out.println("Start detection of " + detector.getSmellName() + " ...");
            long startTime = System.nanoTime();
            long startMemory = calculateMemoryConsumption();
            printSmells(detector.detect());
            String time = calculateTimeConsumption(startTime);
            String memory = formatMemoryConsumption(calculateMemoryConsumption() - startMemory);
            System.out.println("Finished detection of " + detector.getSmellName() + " in " + time + " (" + memory + ")\n");
        }

        printSmells(Detector.getSmells());

        String totalTime = calculateTimeConsumption(startTotalTime);
        System.out.println("\nFinished detection in " + totalTime);
    }

    private static void printSmells(List<EASmell> smells) {
        for (EASmell smell : smells) {
            System.out.println(smell.toString());
        }
    }

    private static String calculateTimeConsumption(long start) {
        long time = (System.nanoTime() - start) / 1000 / 1000;
        if (time < 1000) return "" + time + " ms";
        return "" + (time / 1000.) + " s";
    }

    private static long calculateMemoryConsumption() {
        System.gc();
        System.gc();
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    private static String formatMemoryConsumption(long bytes) {
        if (bytes < 0) return "0 Byte";
        if (bytes < 1024) return "" + bytes + " Byte";
        double b = bytes / 1024.;
        if (b < 1024.) return "" + b + " KByte";
        return "" + (b / 1024.) + " MByte";
    }
}

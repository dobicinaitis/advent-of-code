package dev.dobicinaitis.advent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class Day5 extends Puzzle {

    public Long getFirstPuzzleSolution(String filename) {
        final List<String> input = Utils.getFileContent(filename);
        final Almanac almanac = parseInput(input);
        log.debug("Almanac: {}", almanac);
        return almanac.seedIds.parallelStream().mapToLong(seedId -> {
            final long soilId = calculateTargetId(seedId, almanac.getSeedToSoilMapping());
            final long fertilizerId = calculateTargetId(soilId, almanac.getSoilToFertilizerMapping());
            final long waterId = calculateTargetId(fertilizerId, almanac.getFertilizerToWaterMapping());
            final long lightId = calculateTargetId(waterId, almanac.getWaterToLightMapping());
            final long temperatureId = calculateTargetId(lightId, almanac.getLightToTemperatureMapping());
            final long humidityId = calculateTargetId(temperatureId, almanac.getTemperatureToHumidityMapping());
            final long locationId = calculateTargetId(humidityId, almanac.getHumidityToLocationMapping());
            return locationId;
        }).min().orElseThrow();
    }

    public Long getSecondPuzzleSolution(String filename) {
        final List<String> input = Utils.getFileContent(filename);
        final Almanac almanac = parseInput(input);
        final List<Long> seedIdRanges = almanac.getSeedIds(); // turns out that the seed IDs are actually ranges
        if (seedIdRanges.size() % 2 != 0) {
            throw new RuntimeException("Seed ID ranges are not even");
        }
        // let's go the other way around and find the lowest possible location IDs that maps to a seed ID
        long locationId = -1;
        // brute force 🔨
        while (true) {
            locationId++;
            final long humidityId = calculateSourceId(locationId, almanac.getHumidityToLocationMapping());
            final long temperatureId = calculateSourceId(humidityId, almanac.getTemperatureToHumidityMapping());
            final long lightId = calculateSourceId(temperatureId, almanac.getLightToTemperatureMapping());
            final long waterId = calculateSourceId(lightId, almanac.getWaterToLightMapping());
            final long fertilizerId = calculateSourceId(waterId, almanac.getFertilizerToWaterMapping());
            final long soilId = calculateSourceId(fertilizerId, almanac.getSoilToFertilizerMapping());
            final long seedId = calculateSourceId(soilId, almanac.getSeedToSoilMapping());
            // check if the seed ID is within valid seed ranges
            for (int i = 0; i < seedIdRanges.size(); i += 2) {
                final long rangeStart = seedIdRanges.get(i);
                final long rangeEnd = rangeStart + seedIdRanges.get(i + 1) - 1;
                if (seedId >= rangeStart && seedId <= rangeEnd) {
                    // bingo!
                    return locationId;
                }
            }
        }
    }

    private Almanac parseInput(List<String> input) {
        // extract seeds from the first line
        final String seedLine = input.get(0);
        final String seedNumberPart = seedLine.substring(seedLine.indexOf(":") + 2);
        final List<Long> seeds = Arrays.stream(seedNumberPart.split(" "))
                .mapToLong(Long::parseLong)
                .boxed()
                .toList();

        final Almanac almanac = new Almanac();
        almanac.setSeedIds(seeds);

        // extract mappings
        int lineIndex = 2; // skip the seed line and the empty one after it
        while (lineIndex < input.size()) {
            final String line = input.get(lineIndex);
            if (line.endsWith("map:")) {
                final String mappingName = line.replace(" map:", "");
                final List<List<Long>> mapping = extractMapping(input.subList(lineIndex + 1, input.size()));
                switch (mappingName) {
                    case "seed-to-soil" -> almanac.setSeedToSoilMapping(mapping);
                    case "soil-to-fertilizer" -> almanac.setSoilToFertilizerMapping(mapping);
                    case "fertilizer-to-water" -> almanac.setFertilizerToWaterMapping(mapping);
                    case "water-to-light" -> almanac.setWaterToLightMapping(mapping);
                    case "light-to-temperature" -> almanac.setLightToTemperatureMapping(mapping);
                    case "temperature-to-humidity" -> almanac.setTemperatureToHumidityMapping(mapping);
                    case "humidity-to-location" -> almanac.setHumidityToLocationMapping(mapping);
                    default -> throw new RuntimeException("Unknown mapping");
                }
                lineIndex += mapping.size() + 2; // + the next empty line
            } else {
                log.error("Something is off. Line: {}", line);
                break;
            }
        }
        return almanac;
    }

    private List<List<Long>> extractMapping(List<String> almanacSection) {
        final List<List<Long>> mapping = new ArrayList<>();
        for (String line : almanacSection) {
            // start of next section
            if (line.isEmpty()) {
                break;
            }
            final List<Long> lineElements = Arrays.stream(line.split(" "))
                    .mapToLong(Long::parseLong)
                    .boxed()
                    .toList();
            mapping.add(lineElements);
        }
        return mapping;
    }

    private long calculateTargetId(final long sourceId, final List<List<Long>> mapping) {
        for (List<Long> mappingLine : mapping) {
            final long sourceRangeStart = mappingLine.get(1);
            final long sourceRangeLength = mappingLine.get(2) - 1;
            if (sourceId >= sourceRangeStart && sourceId <= sourceRangeStart + sourceRangeLength) {
                final long sourceRangeStartOffset = sourceId - sourceRangeStart;
                final long targetRangeStart = mappingLine.get(0);
                return targetRangeStart + sourceRangeStartOffset;
            }
        }
        return sourceId;
    }


    private long calculateSourceId(final long targetId, final List<List<Long>> mapping) {
        for (List<Long> mappingLine : mapping) {
            final long targetRangeStart = mappingLine.get(0);
            final long targetRangeLength = mappingLine.get(2) - 1;
            if (targetId >= targetRangeStart && targetId <= targetRangeStart + targetRangeLength) {
                final long targetRangeStartOffset = targetId - targetRangeStart;
                final long sourceRangeStart = mappingLine.get(1);
                return sourceRangeStart + targetRangeStartOffset;
            }
        }
        return targetId;
    }

    @Data
    static class Almanac {
        private List<Long> seedIds;
        private List<List<Long>> seedToSoilMapping;
        private List<List<Long>> soilToFertilizerMapping;
        private List<List<Long>> fertilizerToWaterMapping;
        private List<List<Long>> waterToLightMapping;
        private List<List<Long>> lightToTemperatureMapping;
        private List<List<Long>> temperatureToHumidityMapping;
        private List<List<Long>> humidityToLocationMapping;
    }
}

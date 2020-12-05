package dev.dobicinaitis.advent;

import java.util.List;
import java.util.NoSuchElementException;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class Day5 {

    public static long getFirstPuzzleSolution(String filename){
        List<String> boardingPasses = Utils.getFileContent(filename);
        Integer maxSeatId = boardingPasses.stream()
                .mapToInt(boardingPass -> getSeatId(
                        getSeatRowFromBoardingPass(boardingPass,0,127,7),
                        getSeatColumnFromBoardingPass(boardingPass, 0,7,3)))
                .max().orElseThrow(NoSuchElementException::new);
        return maxSeatId;
    }

    public static long getSecondPuzzleSolution(String filename){
        List<String> boardingPasses = Utils.getFileContent(filename);
        int[] seatIds = boardingPasses.stream()
                .mapToInt(boardingPass -> getSeatId(
                        getSeatRowFromBoardingPass(boardingPass, 0, 127, 7),
                        getSeatColumnFromBoardingPass(boardingPass, 0, 7, 3)))
                .sorted()
                .filter(seatId -> seatId > getSeatId(0,7) && seatId < getSeatId(127,1))
                .toArray();
        //log.debug("seat IDs:\n" + Arrays.toString(seatIds));
        return getEmptySeatId(seatIds);
    }

    @SneakyThrows
    public static int getEmptySeatId(int[] sortedSeatIds){
        for (int i = 0; i < sortedSeatIds.length; i++){
            if (sortedSeatIds[i + 1] - sortedSeatIds[i] == 2){
                return sortedSeatIds[i] + 1;
            }
        }
        throw new Exception("You shall not sit!");
    }

    public static int getSeatId(int row, int column){
        return row * 8 + column;
    }

    @SneakyThrows
    public static int getSeatRowFromBoardingPass(String boardingPass, int seatMinRow, int seatMaxRow, int rowIndicatorLength){
        for(int i = 0; i < rowIndicatorLength; i++){
            char rowIndicator = boardingPass.charAt(i);
            if (rowIndicator == 'F'){
                seatMaxRow = getLowerHalf(seatMinRow,seatMaxRow);
            }
            else if (rowIndicator == 'B'){
                seatMinRow = getUpperHalf(seatMinRow,seatMaxRow);
            }
            //log.debug("{},  row range: {} - {}", rowIndicator, seatMinRow, seatMaxRow);
        }

        if (seatMinRow != seatMaxRow){
            throw new Exception("Nope! debug, code, test, repeat :p");
        }

        return seatMinRow;
    }

    @SneakyThrows
    public static int getSeatColumnFromBoardingPass(String boardingPass, int seatMinColumn, int seatMaxColumn, int columnIndicatorLength){
        for(int i = boardingPass.length() - columnIndicatorLength; i < boardingPass.length(); i++){
            char columnIndicator = boardingPass.charAt(i);
            if (columnIndicator == 'L'){
                seatMaxColumn = getLowerHalf(seatMinColumn,seatMaxColumn);
            }
            else if (columnIndicator == 'R'){
                seatMinColumn = getUpperHalf(seatMinColumn, seatMaxColumn);
            }
            //log.debug("{},  column range: {} - {}", columnIndicator, seatMinColumn, seatMaxColumn);
        }

        if (seatMinColumn != seatMaxColumn){
            throw new Exception("Nope! debug, code, test, repeat :p");
        }

        return seatMinColumn;
    }

    public static int getUpperHalf(int min, int max){
        return max - (max - min) / 2;
    }

    public static int getLowerHalf(int min, int max){
        return min + (max - min) / 2;
    }
}

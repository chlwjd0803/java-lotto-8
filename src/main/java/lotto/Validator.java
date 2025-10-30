package lotto;

import java.util.HashSet;
import java.util.List;

public class Validator {
    private static final String NUMBER_LIST_REGEX = "^\\d+(,\\d+)*$";

    // 구입금액 입력 예외
    public static Integer getMoneyAndcheckMoneyFormat(String input){
        try{
            Integer money = Integer.parseInt(input);
            if(money % 1000 != 0)
                throw new IllegalArgumentException("1,000원으로 나누어 떨어지지 않음");
            return money/1000;
        } catch (NumberFormatException e){
            System.out.println("[ERROR] 구입금액이 올바른 정수형태가 아닙니다.");
            return null;
        } catch (IllegalArgumentException e){
            System.out.println("[ERROR] 구입 후 잔액이 남도록 입력할 수 없습니다.");
            return null;
        }
    }

    // 정규식을 이용하여 쉼표 구분자로 숫자를 정리하였는지
    public static Boolean checkLottoInputFormat(String input){
        try{
            if(!input.matches(NUMBER_LIST_REGEX))
                throw new IllegalArgumentException("입력 형태가 올바르지 않음");
            return true;
        } catch (IllegalArgumentException e){
            System.out.println("[ERROR] 입력 형태가 올바르지 않습니다. 쉼표 구분자를 구분하여 정수를 입력해주세요.");
            return false;
        }
    }

    // 입력한 로또 번호가 정수형태인지 검사
    public static Boolean insertAndCheckWinningNumbers(String input, List<Integer> winningNumbers){
        String[] inputSplit = input.split(",");

        try{
            for(String number : inputSplit){
                winningNumbers.add(Integer.parseInt(number));
            }
            return true;
        } catch (NumberFormatException e){
            System.out.println("[ERROR] 입력 로또번호가 올바른 정수형태가 아닙니다.");
            return false;
        }
    }

    // 로또 번호가 6개로 입력되었는지 검사
    public static Boolean checkWinningNumbersIsSix(List<Integer> winningNumbers){
        try{
            if(winningNumbers.size() != 6)
                throw new IllegalArgumentException("로또번호 6개 불일치");
            return true;
        } catch (IllegalArgumentException e){
            System.out.println("[ERROR] 로또 번호는 6개여야 합니다.");
            return false;
        }
    }

    public static Boolean checkWinningNumbersIsUnique(List<Integer> winningNumbers){
        try{
            HashSet<Integer> winningNumberSet = new HashSet<>(winningNumbers);
            if(winningNumberSet.size() != 6)
                throw new IllegalArgumentException("중복된 원소가 존재함");
            return true;
        } catch (IllegalArgumentException e){
            System.out.println("[ERROR] 중복된 번호를 입력할 수 없습니다.");
            return false;
        }
    }

    public static Boolean checkWinningNumbersInRange(List<Integer> winningNumbers){
        try{
            for(Integer number : winningNumbers){
                if(number < 1 || number > 45)
                    throw new IllegalArgumentException("범위를 넘어가는 원소가 존재");
            }
            return true;
        } catch (IllegalArgumentException e){
            System.out.println("[ERROR] 번호의 범위인 1~45를 넘어갈 수 없습니다.");
            return false;
        }
    }

    public static Integer insertAndCheckBonusNumber(String input){
        try{
            return Integer.parseInt(input);
        } catch (NumberFormatException e){
            System.out.println("[ERROR] 입력한 보너스 번호가 올바른 정수형태가 아닙니다.");
            return null;
        }
    }
}

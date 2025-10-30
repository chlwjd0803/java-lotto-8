package lotto;

import camp.nextstep.edu.missionutils.Console;

import java.util.*;

public class Application {
    private static final String NUMBER_LIST_REGEX = "^\\d+(,\\d+)*$";

    // 로또 구입금액 입력
    private Integer retNumOfLotto(){
        while(true){
            System.out.println("구입금액을 입력해 주세요.");
            try{
                Integer money = Integer.parseInt(Console.readLine());
                if(money % 1000 != 0)
                    throw new IllegalArgumentException("1,000원으로 나누어 떨어지지 않음");
                return money / 1000;
            } catch (NumberFormatException e){
                System.out.println("[ERROR] 구입금액이 올바른 정수형태가 아닙니다.");
            } catch (IllegalArgumentException e){
                System.out.println("[ERROR] 구입 후 잔액이 남도록 입력할 수 없습니다.");
            }
        }
    }

    // 정규식을 이용하여 쉼표 구분자로 숫자를 정리하였는지
    private Boolean checkLottoInputFormat(String input){
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
    private Boolean insertWinningNumbers(String input, List<Integer> winningNumbers){
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
    private Boolean checkWinningNumbersIsSix(List<Integer> winningNumbers){
        try{
            if(winningNumbers.size() != 6)
                throw new IllegalArgumentException("로또번호 6개 불일치");
            return true;
        } catch (IllegalArgumentException e){
            System.out.println("[ERROR] 로또 번호는 6개여야 합니다.");
            return false;
        }
    }

    private Boolean checkWinningNumbersIsUnique(List<Integer> winningNumbers){
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

    private Boolean checkWinningNumbersInRange(List<Integer> winningNumbers){
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

    // 당첨 번호 입력
    private List<Integer> retWinningLotto(){
        while(true){
            System.out.println("당첨 번호를 입력해 주세요.");
            String input = Console.readLine();
            List<Integer> winningNumbers = new ArrayList<>();

            if(!checkLottoInputFormat(input)) continue; // 정규식을 따르는지 검사
            if(!insertWinningNumbers(input, winningNumbers)) continue; // List에 원소를 집어넣으며 정수형태 검증
            if(!checkWinningNumbersIsSix(winningNumbers)) continue; // 여섯개의 번호만 입력했는지 검사
            if(!checkWinningNumbersInRange(winningNumbers)) continue; // 1~45 범위의 번호들만 입력했는지 검사
            if(!checkWinningNumbersIsUnique(winningNumbers)) continue; // 번호들이 중복됨이 있는지 검사

            return winningNumbers;
        }
    }


    // 보너스 번호 입력
    private Integer retBonusNumber(){
        while(true){
            try{
                System.out.println("보너스 번호를 입력해 주세요.");
                return Integer.parseInt(Console.readLine());
            } catch (NumberFormatException e){
                System.out.println("[ERROR] 입력한 보너스 번호가 올바른 정수형태가 아닙니다.");
            }
        }
    }

    // 총합 구하기
    private Integer totalRewardMoney(HashMap<Reward, Integer> rewardMap){
        Integer total = 0;
        for(Reward reward : Reward.values()){
            // 총합에 보상금 * 당첨횟수를 더함
            total += reward.getRewardMoney() * rewardMap.get(reward);
        }
        return total;
    }

    // 실행
    public void run(){
        // 구입한 로또의 개수
        Integer numOfLotto = retNumOfLotto();
        // 로또 생성
        List<Lotto> lottos = Lotto.makeLottos(numOfLotto);
        // 로또들 출력
        Lotto.printLottos(numOfLotto, lottos);
        // 당첨번호 입력
        List<Integer> winningLotto = retWinningLotto();
        // 보너스 번호 입력
        Integer bonus = retBonusNumber();
        // 각 당첨 통계 맵
        HashMap<Reward, Integer> rewardMap = Reward.status(lottos, new TreeSet<>(winningLotto), bonus);
        Reward.printStatus(rewardMap);
        // 상금 총합
        Integer total = totalRewardMoney(rewardMap);
        System.out.println("총 수익률은 " + (total / (numOfLotto * 1000) * 100) + "%입니다.");
    }

    public static void main(String[] args) {
        new Application().run();
    }
}

package lotto;



import camp.nextstep.edu.missionutils.Randoms;
import camp.nextstep.edu.missionutils.Console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Application {

    // 로또 구입금액 입력
    private Integer retNumOfLotto(){
        System.out.println("구입금액을 입력해 주세요.");
        Integer money = Integer.parseInt(Console.readLine());
        return money / 1000;
    }

    // 로또번호 랜덤생성 매 회
    private Lotto makeLotto(){
        // 하나의 로또 번호 저장
        Set<Integer> numberSet = new TreeSet<>(); // 매 회 초기화

        // 집합은 중복을 허용하지 않으므로 6개까지 뽑으면 된다.
        while(numberSet.size() < 6){
            numberSet.add(Randoms.pickNumberInRange(1, 45));
        }

        List<Integer> numbers = new ArrayList<>(numberSet); // 리스트로 변환
        return new Lotto(numbers);
    }

    // 로또번호 랜덤생성 전체
    private List<Lotto> makeLottos(Integer numOfLotto){
        // 로또들을 담는 자료형
        List<Lotto> lottos = new ArrayList<>();
        for(int i = 0; i < numOfLotto; i++){
            lottos.add(makeLotto());
        }
        return lottos;
    }

    // 만든 로또들 출력
    private void printLottos(Integer numOfLotto, List<Lotto> lottos){
        System.out.println(numOfLotto + "개를 구매했습니다.");

        for(Lotto lotto : lottos){
            System.out.println(lotto.getNumbers().toString());
        }
    }

    // 당첨 번호 입력
    private List<Integer> retWinningLotto(){
        System.out.println("당첨 번호를 입력해 주세요.");
        String input = Console.readLine();

        String[] inputSplit = input.split(",");
        List<Integer> winningNumbers = new ArrayList<>();

        for(String number : inputSplit){
            winningNumbers.add(Integer.parseInt(number));
        }

        return winningNumbers;
    }

    // 보너스 번호 입력
    private Integer retBonusNumber(){
        System.out.println("보너스 번호를 입력해 주세요.");
        return Integer.parseInt(Console.readLine());
    }

    // 당첨현황 저장
    private HashMap<Reward, Integer> status(List<Lotto> lottos, TreeSet<Integer> winningLotto, Integer bonus){
        Integer total = 0;
        HashMap<Reward, Integer> rewardMap = Reward.getRewardMap();

        for(Lotto lotto : lottos){
            TreeSet<Integer> lottoSet = new TreeSet<>(lotto.getNumbers());
            TreeSet<Integer> temp = new TreeSet<>(lottoSet);
            temp.retainAll(winningLotto);
            Reward reward = Reward.getReward(temp.size(), lottoSet.contains(bonus));
            rewardMap.put(reward, rewardMap.get(reward) + 1);
        }
        return rewardMap;
    }

    // 당첨현황 출력
    private void printStatus(HashMap<Reward, Integer> rewardMap){
        List<Reward> rewards = Reward.getReverseRewardWithoutMiss();
        System.out.println("당첨 통계");
        System.out.println("---");
        for(Reward reward : rewards){
            String formattedMoney = String.format("%,d", reward.getRewardMoney());

            System.out.print(reward.getMatchCount() + "개 일치");
            if(reward.getBonusMatch())
                System.out.print(", 보너스 볼 일치");
            System.out.println(" (" + formattedMoney + "원) - " + rewardMap.get(reward) + "개");
        }
    }

    // 실행
    public void run(){
        // 구입한 로또의 개수
        Integer numOfLotto = retNumOfLotto();
        // 로또 생성
        List<Lotto> lottos = makeLottos(numOfLotto);
        // 로또들 출력
        printLottos(numOfLotto, lottos);
        // 당첨번호 입력
        List<Integer> winningLotto = retWinningLotto();
        // 보너스 번호 입력
        Integer bonus = retBonusNumber();
        // 각 당첨 통계 맵
        HashMap<Reward, Integer> rewardMap = status(lottos, new TreeSet<>(winningLotto), bonus);
        printStatus(rewardMap);
    }

    public static void main(String[] args) {
        new Application().run();
    }
}

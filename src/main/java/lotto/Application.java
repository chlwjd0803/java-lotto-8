package lotto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import camp.nextstep.edu.missionutils.Randoms;
import camp.nextstep.edu.missionutils.Console;

public class Application {

    // 로또번호 랜덤생성 매 회
    private Lotto makeLotto(){
        // 하나의 로또 번호 저장
        HashSet<Integer> numberSet = new HashSet<>(); // 매 회 초기화

        // 집합은 중복을 허용하지 않으므로 6개까지 뽑으면 된다.
        while(numberSet.size() < 6){
            numberSet.add(Randoms.pickNumberInRange(1, 45));
        }

        List<Integer> numbers = new ArrayList<>(numberSet); // 리스트로 변환
        Collections.sort(numbers); // 오름차순으로 정렬
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


    public void run(){
        System.out.println("구입금액을 입력해 주세요.");
        Integer money = Integer.parseInt(Console.readLine());
        // 구입한 로또의 개수
        Integer numOfLotto = money / 1000; // 로또의 개수만큼 변경

        // 로또 생성
        List<Lotto> lottos = makeLottos(numOfLotto);




    }

    public static void main(String[] args) {
        new Application().run();
    }
}

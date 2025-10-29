package lotto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import camp.nextstep.edu.missionutils.Randoms;
import camp.nextstep.edu.missionutils.Console;

public class Application {
    public void run(){
        System.out.println("구입금액을 입력해 주세요.");
        Integer money = Integer.parseInt(Console.readLine());
        // 구입한 로또의 개수
        Integer numOfLotto = money / 1000; // 로또의 개수만큼 변경
    }
    public static void main(String[] args) {
        new Application().run();
    }
}

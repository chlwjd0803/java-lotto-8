package lotto;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public enum Reward {
    // 6개 번호 일치 / 2,000,000,000원
    FIRST(6, false, 2000000000),

    // 5개 번호 + 보너스 번호 일치 / 30,000,000원
    SECOND(5, true, 30000000),

    // 5개 번호 일치 / 1,500,000원
    THIRD(5, false, 1500000),

    // 4개 번호 일치 / 50,000원
    FOURTH(4, false, 50000),

    // 3개 번호 일치 / 5,000원
    FIFTH(3, false, 5000),

    // 낙첨 : 2개 이하 일치
    MISS(0, false, 0);

    private final Integer matchCount;
    private final Boolean bonusMatch;
    private final Integer rewardMoney;

    Reward(Integer matchCount, Boolean bonusMatch, Integer rewardMoney){
        this.matchCount = matchCount;
        this.bonusMatch = bonusMatch;
        this.rewardMoney = rewardMoney;
    }

    public static Reward getReward(Integer matchCount, Boolean bonusMatch){
        if(matchCount == 6)
            return FIRST;
        if(matchCount == 5 && bonusMatch)
            return SECOND;
        if(matchCount == 5)
            return THIRD;
        if(matchCount == 4)
            return FOURTH;
        if(matchCount == 3)
            return FIFTH;
        return MISS;
    }

    public static HashMap<Reward, Integer> getRewardMap(){
        HashMap<Reward, Integer> rewardMap = new HashMap<>();
        for(Reward reward : Reward.values()){
            rewardMap.put(reward, 0);
        }
        return rewardMap;
    }

    public static List<Reward> getReverseRewardWithoutMiss(){
        return Arrays.asList(FIFTH, FOURTH, THIRD, SECOND, FIRST);
    }

    public Integer getRewardMoney(){
        return rewardMoney;
    }

    public Integer getMatchCount(){
        return matchCount;
    }

    public Boolean getBonusMatch(){
        return bonusMatch;
    }
}

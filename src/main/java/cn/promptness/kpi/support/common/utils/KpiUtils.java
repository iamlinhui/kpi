package cn.promptness.kpi.support.common.utils;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

import static cn.promptness.kpi.support.common.utils.KpiUtils.Level.*;

/**
 * @author Lynn
 * @date 2018/9/17 21:21
 */
public class KpiUtils {

    private static String getLevel(Double score) {
        if (null == score) {
            return null;
        }
        if (score >= A.score) {
            return A.name();
        }
        if (score >= B.score) {
            return B.name();
        }
        if (score >= C.score) {
            return C.name();
        }
        if (score >= D.score) {
            return D.name();
        }
        return E.name();
    }

    public static Map getDistributionByTotal(int size) {

        if (size == 1) {
            return ImmutableMap.of(A.name(), 0, B.name(), 1);
        }
        int a = (int) (size * 0.2);
        int b = (int) (size * 0.7) - a;
        return ImmutableMap.of(A.name(), a, B, b);
    }

    public static Map getAllDistributionByTotal(int size) {

        if (size == 1) {
            return ImmutableMap.of(A, 0, B, 1, C, 0);
        }
        int a = (int) (size * 0.2);
        int b = (int) (size * 0.7) - a;
        int c = size - a - b;
        return ImmutableMap.of(A, a, B, b, C, c);
    }

    public static Map getDistributionByScoreList(List<Double> scoreList) {
        Map<String, Integer> map = Maps.newHashMapWithExpectedSize(5);
        for (Double score : scoreList) {
            if (score == null) {
                continue;
            }
            String level = getLevel(score);
            boolean containsKey = map.containsKey(level);
            if (containsKey) {
                Integer count = map.get(level);
                count++;
                map.put(level, count);
            } else {
                map.put(level, 1);
            }
        }
        return map;
    }


    enum Level {

        /**
         *
         */
        A(90),
        B(80),
        C(70),
        D(60),
        E(50);

        Level(Integer score) {
            this.score = score;
        }

        private Integer score;
    }

}

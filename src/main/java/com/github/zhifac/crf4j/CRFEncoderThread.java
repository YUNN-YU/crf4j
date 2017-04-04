package com.github.zhifac.crf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by zhifac on 2017/3/25.
 */
public class CRFEncoderThread implements Callable<Integer> {
    public List<TaggerImpl> x;
    public int start_i;
    public int threadNum;
    public int zeroone;
    public int err;
    public int size;
    public double obj;
    public double[] expected;

    public CRFEncoderThread(int wsize) {
        if (wsize > 0) {
            expected = new double[wsize];
            Arrays.fill(expected, 0.0);
        }
    }

    public Integer call() {
        obj = 0.0;
        err = zeroone = 0;
        Arrays.fill(expected, 0.0);
        for (int i = start_i; i < size; i = i + threadNum) {
            obj += x.get(i).gradient(expected);
            int errorNum = x.get(i).eval();
            err += errorNum;
            if (errorNum != 0) {
                ++zeroone;
            }
        }
        return err;
    }
}

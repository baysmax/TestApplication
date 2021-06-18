package com.baymax.testapplication;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 *
 *
 * @Created by  Baymax on 2021/6/9.
 * @e-mail 761241917@qq.com
 */
public class BezierEvaluator  implements TypeEvaluator<PointF> {

    private PointF mFlagPoint;

    public BezierEvaluator(PointF flagPoint) {
        mFlagPoint = flagPoint;
    }

    @Override
    public PointF evaluate(float v, PointF pointF, PointF t1) {
        return BezierUtil.CalculateBezierPointForQuadratic(v, pointF, mFlagPoint, t1);
    }
}
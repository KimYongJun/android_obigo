package com.obigo.menu.Eases;

/**
 * Created by Weiping on 2016/3/3.
 */

public enum EaseType {
    
    EaseInSine(com.obigo.menu.Eases.EaseInSine.class),
    EaseOutSine(com.obigo.menu.Eases.EaseOutSine.class),
    EaseInOutSine(EaseInOutSine.class),

    EaseInQuad(EaseInQuad.class),
    EaseOutQuad(EaseOutQuad.class),
    EaseInOutQuad(com.obigo.menu.Eases.EaseInOutQuad.class),

    EaseInCubic(com.obigo.menu.Eases.EaseInCubic.class),
    EaseOutCubic(EaseOutCubic.class),
    EaseInOutCubic(EaseInOutCubic.class),

    EaseInQuart(EaseInQuart.class),
    EaseOutQuart(com.obigo.menu.Eases.EaseOutQuart.class),
    EaseInOutQuart(EaseInOutQuart.class),

    EaseInQuint(com.obigo.menu.Eases.EaseInQuint.class),
    EaseOutQuint(EaseOutQuint.class),
    EaseInOutQuint(com.obigo.menu.Eases.EaseInOutQuint.class),

    EaseInExpo(com.obigo.menu.Eases.EaseInExpo.class),
    EaseOutExpo(com.obigo.menu.Eases.EaseOutExpo.class),
    EaseInOutExpo(com.obigo.menu.Eases.EaseInOutExpo.class),

    EaseInCirc(EaseInCirc.class),
    EaseOutCirc(EaseOutCirc.class),
    EaseInOutCirc(com.obigo.menu.Eases.EaseInOutCirc.class),

    EaseInBack(com.obigo.menu.Eases.EaseInBack.class),
    EaseOutBack(EaseOutBack.class),
    EaseInOutBack(EaseInOutBack.class),

    EaseInElastic(EaseInElastic.class),
    EaseOutElastic(EaseOutElastic.class),
    EaseInOutElastic(com.obigo.menu.Eases.EaseInOutElastic.class),

    EaseInBounce(EaseInBounce.class),
    EaseOutBounce(com.obigo.menu.Eases.EaseOutBounce.class),
    EaseInOutBounce(EaseInOutBounce.class),

    Linear(com.obigo.menu.Eases.Linear.class);

    private Class easingType;

    /**
     * ease animation helps to make the movement more real
     * @param easingType
     */
    EaseType(Class easingType) {
        this.easingType = easingType;
    }

    public float getOffset(float offset) {
        try {
            return ((CubicBezier) easingType.getConstructor().newInstance()).getOffset(offset);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error("CubicBezier init error.");
        }
    }

}

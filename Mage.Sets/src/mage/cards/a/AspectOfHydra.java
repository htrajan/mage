package mage.cards.a;

import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AspectOfHydra extends CardImpl {

    public AspectOfHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Target creature gets +X/+X until end of turn, where X is your devotion to green.
        Effect effect = new BoostTargetEffect(DevotionCount.G, DevotionCount.G, Duration.EndOfTurn, true);
        effect.setText("Target creature gets +X/+X until end of turn, where X is your devotion to green");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(new ValueHint("Devotion to green", DevotionCount.G));
    }

    public AspectOfHydra(final AspectOfHydra card) {
        super(card);
    }

    @Override
    public AspectOfHydra copy() {
        return new AspectOfHydra(this);
    }
}

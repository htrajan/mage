package mage.cards.p;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.PreventDamageAndRemoveCountersEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.keyword.EscapeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 * @author TheElk801
 */
public final class PolukranosUnchained extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(AnotherPredicate.instance);
    }

    public PolukranosUnchained(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Polukranos enters the battlefield with six +1/+1 counters on it. It escapes with twelve +1/+1 counters on it instead.
        this.addAbility(new EntersBattlefieldAbility(new PolukranosUnchainedEffect()));

        // If damage would be dealt to Polukranos while it has a +1/+1 counter on it, prevent that damage and remove that many +1/+1 counters from it.
        this.addAbility(new SimpleStaticAbility(new PreventDamageAndRemoveCountersEffect(true)));

        // {1}{B}{G}: Polukranos fights another target creature.
        Ability ability = new SimpleActivatedAbility(new FightTargetSourceEffect(), new ManaCostsImpl("{1}{B}{G}"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Escape—{4}{B}{G}, Exile six other cards from your graveyard.
        this.addAbility(new EscapeAbility(this, "{4}{B}{G}", 6));
    }

    private PolukranosUnchained(final PolukranosUnchained card) {
        super(card);
    }

    @Override
    public PolukranosUnchained copy() {
        return new PolukranosUnchained(this);
    }
}

class PolukranosUnchainedEffect extends OneShotEffect {

    PolukranosUnchainedEffect() {
        super(Outcome.BoostCreature);
        staticText = "with six +1/+1 counters on it. It escapes with twelve +1/+1 counters on it instead";
    }

    private PolukranosUnchainedEffect(final PolukranosUnchainedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null && source.getAbilityType() == AbilityType.STATIC) {
            permanent = game.getPermanentEntering(source.getSourceId());
        }
        if (permanent == null) {
            return false;
        }
        SpellAbility spellAbility = (SpellAbility) getValue(EntersBattlefieldEffect.SOURCE_CAST_SPELL_ABILITY);
        int counters = (spellAbility instanceof EscapeAbility) ? 12 : 6;
        if (!spellAbility.getSourceId().equals(source.getSourceId())
                || permanent.getZoneChangeCounter(game) != spellAbility.getSourceObjectZoneChangeCounter()
                || !spellAbility.getSourceId().equals(source.getSourceId())) {
            return false;
        }
        List<UUID> appliedEffects = (ArrayList<UUID>) this.getValue("appliedEffects");
        permanent.addCounters(CounterType.P1P1.createInstance(counters), source, game, appliedEffects);
        return true;
    }

    @Override
    public PolukranosUnchainedEffect copy() {
        return new PolukranosUnchainedEffect(this);
    }

}
/*
 * Copyright Gert Wijns gert.wijns@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.shad.tsqb.restrictions;

import static be.shad.tsqb.restrictions.RestrictionOperator.GREATER_THAN;
import static be.shad.tsqb.restrictions.RestrictionOperator.GREATER_THAN_EQUAL;
import static be.shad.tsqb.restrictions.RestrictionOperator.LESS_THAN;
import be.shad.tsqb.restrictions.named.SingleNamedParameterBinder;
import be.shad.tsqb.restrictions.predicate.RestrictionPredicate;
import be.shad.tsqb.values.DirectTypeSafeValue;
import be.shad.tsqb.values.TypeSafeValue;

/**
 * Restrictions for numbers. Number specific restrictions are added here.
 *
 * @see OnGoingRestrictionImpl
 */
public class OnGoingNumberRestrictionImpl
        extends OnGoingRestrictionImpl<Number, ContinuedOnGoingNumberRestriction, OnGoingNumberRestriction>
        implements OnGoingNumberRestriction, ContinuedOnGoingNumberRestriction {

    public OnGoingNumberRestrictionImpl(RestrictionsGroupInternal group,
            RestrictionNodeType restrictionNodeType, Number argument) {
        super(group, restrictionNodeType, argument);
    }

    @SuppressWarnings("unchecked")
    public OnGoingNumberRestrictionImpl(RestrictionsGroupInternal group,
            RestrictionNodeType restrictionNodeType, TypeSafeValue<? extends Number> argument) {
        super(group, restrictionNodeType, (TypeSafeValue<Number>) argument);
    }

    @Override
    protected OnGoingNumberRestrictionImpl createContinuedOnGoingRestriction(
            RestrictionNodeType restrictionNodeType, TypeSafeValue<Number> previousValue) {
        return new OnGoingNumberRestrictionImpl(group, restrictionNodeType, previousValue);
    }

    @Override
    protected OnGoingNumberRestriction createOriginalOnGoingRestriction(
            RestrictionNodeType restrictionNodeType, TypeSafeValue<Number> previousValue) {
        return createContinuedOnGoingRestriction(restrictionNodeType, previousValue);
    }

    @Override
    protected Class<Number> getSupportedValueClass() {
        return Number.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContinuedOnGoingNumberRestriction lt(Number value) {
        return lt(value, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContinuedOnGoingNumberRestriction lt(TypeSafeValue<Number> value) {
        return addRestrictionAndContinue(startValue, LESS_THAN, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContinuedOnGoingNumberRestriction gt(Number value) {
        return gt(value, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContinuedOnGoingNumberRestriction gt(TypeSafeValue<Number> value) {
        return addRestrictionAndContinue(startValue, GREATER_THAN, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContinuedOnGoingNumberRestriction lte(Number value) {
        return lte(value, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContinuedOnGoingNumberRestriction lte(TypeSafeValue<Number> value) {
        return addRestrictionAndContinue(startValue, RestrictionOperator.LESS_THAN_EQUAL, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContinuedOnGoingNumberRestriction gte(Number value) {
        return gte(value, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContinuedOnGoingNumberRestriction gte(TypeSafeValue<Number> value) {
        return addRestrictionAndContinue(startValue, GREATER_THAN_EQUAL, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContinuedOnGoingNumberRestriction ngt(Number value) {
        return lte(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContinuedOnGoingNumberRestriction ngt(TypeSafeValue<Number> value) {
        return lte(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContinuedOnGoingNumberRestriction nlt(Number value) {
        return gte(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContinuedOnGoingNumberRestriction nlt(TypeSafeValue<Number> value) {
        return gte(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SingleNamedParameterBinder<Number, ContinuedOnGoingNumberRestriction, OnGoingNumberRestriction> ngt() {
        DirectTypeSafeValue<Number> value = createDummyDirectValue();
        return createNamedParameterBinder(value, ngt(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SingleNamedParameterBinder<Number, ContinuedOnGoingNumberRestriction, OnGoingNumberRestriction> nlt() {
        DirectTypeSafeValue<Number> value = createDummyDirectValue();
        return createNamedParameterBinder(value, nlt(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SingleNamedParameterBinder<Number, ContinuedOnGoingNumberRestriction, OnGoingNumberRestriction> gte() {
        DirectTypeSafeValue<Number> value = createDummyDirectValue();
        return createNamedParameterBinder(value, gte(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SingleNamedParameterBinder<Number, ContinuedOnGoingNumberRestriction, OnGoingNumberRestriction> lte() {
        DirectTypeSafeValue<Number> value = createDummyDirectValue();
        return createNamedParameterBinder(value, lte(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SingleNamedParameterBinder<Number, ContinuedOnGoingNumberRestriction, OnGoingNumberRestriction> gt() {
        DirectTypeSafeValue<Number> value = createDummyDirectValue();
        return createNamedParameterBinder(value, gt(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SingleNamedParameterBinder<Number, ContinuedOnGoingNumberRestriction, OnGoingNumberRestriction> lt() {
        DirectTypeSafeValue<Number> value = createDummyDirectValue();
        return createNamedParameterBinder(value, lt(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContinuedOnGoingNumberRestriction gte(Number value, RestrictionPredicate predicate) {
        return gte(toValue(value, predicate));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContinuedOnGoingNumberRestriction lte(Number value, RestrictionPredicate predicate) {
        return lte(toValue(value, predicate));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContinuedOnGoingNumberRestriction gt(Number value, RestrictionPredicate predicate) {
        return gt(toValue(value, predicate));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContinuedOnGoingNumberRestriction lt(Number value, RestrictionPredicate predicate) {
        return lt(toValue(value, predicate));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContinuedOnGoingNumberRestriction nlt(Number value, RestrictionPredicate predicate) {
        return nlt(toValue(value, predicate));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContinuedOnGoingNumberRestriction ngt(Number value, RestrictionPredicate predicate) {
        return ngt(toValue(value, predicate));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContinuedOnGoingNumberRestriction isZero() {
        return eq(0D);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContinuedOnGoingNumberRestriction isNegative() {
        return lt(0D);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContinuedOnGoingNumberRestriction isPositive() {
        return gt(0D);
    }
}

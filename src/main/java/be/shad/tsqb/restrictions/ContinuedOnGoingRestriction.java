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

/**
 * Exposes methods to continue chaining for the same left value (or right if left was null)
 * of the previous restriction.
 * <p>
 * When using methods of the OnGoingRestriction, the and() is implicit.
 */
public interface ContinuedOnGoingRestriction<VAL, CONTINUED extends ContinuedOnGoingRestriction<VAL, CONTINUED, ORIGINAL>,
    ORIGINAL extends OnGoingRestriction<VAL, CONTINUED, ORIGINAL>>
    extends OnGoingRestriction<VAL, CONTINUED, ORIGINAL>, ContinuedRestrictionChainable {

    /**
     * Creates a new OnGoingRestriction of the original type where the
     * left value is pre-filled with the previous left value.
     * <p>
     * This restriction is added as and restriction.
     */
    ORIGINAL and();

    /**
     * Creates a new OnGoingRestriction of the original type where the
     * left value is pre-filled with the previous left value.
     * <p>
     * This restriction is added as or restriction.
     */
    ORIGINAL or();

}

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
package be.shad.tsqb.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import be.shad.tsqb.domain.House;
import be.shad.tsqb.domain.Style;
import be.shad.tsqb.domain.people.Person;
import be.shad.tsqb.domain.people.Person.Sex;
import be.shad.tsqb.query.TypeSafeSubQuery;
import be.shad.tsqb.restrictions.RestrictionsGroup;
import be.shad.tsqb.restrictions.RestrictionsGroupFactory;
import be.shad.tsqb.values.TypeSafeValue;

public class WhereTests extends TypeSafeQueryTest {

    /**
     * Where left is a reference and right is a value
     */
    @Test
    public void whereEnumValueEquals() {
        House house = query.from(House.class);
        query.where(house.getStyle()).eq(Style.the1980s);
        validate(" from House hobj1 where hobj1.style = :np1", Style.the1980s);
    }

    /**
     * Where left and right are both references
     */
    @Test
    public void whereByReference() {
        House house1 = query.from(House.class);
        House house2 = query.from(House.class);
        query.where(house1.getStyle()).eq(house2.getStyle());
        validate(" from House hobj1, House hobj2 where hobj1.style = hobj2.style");
    }

    /**
     * Where left and right are both references (using select).
     */
    @Test
    public void whereByReferenceUsingTypeSafeValueSelect() {
        House house1 = query.from(House.class);
        House house2 = query.from(House.class);

        TypeSafeValue<Style> value = query.toValue(house1.getStyle());
        query.where(value.select()).eq(house2.getStyle());
        validate(" from House hobj1, House hobj2 where hobj1.style = hobj2.style");
    }

    /**
     * Where left is a reference and right is a subquery value
     */
    @Test
    public void whereBySubquery() {
        House house = query.from(House.class);

        TypeSafeSubQuery<Style> houseSQ = query.subquery(Style.class);
        House houseSQV = houseSQ.from(House.class);
        houseSQ.select(houseSQV.getStyle());

        query.where(house.getStyle()).eq(houseSQ);
        validate(" from House hobj1 where hobj1.style = (select hobj2.style from House hobj2)");
    }

    /**
     *
     */
    @Test
    public void whereExists() {
        House house = query.from(House.class);

        TypeSafeSubQuery<Style> houseSQ = query.subquery(Style.class);
        House houseSQV = houseSQ.from(House.class);
        houseSQ.where(houseSQV.getName()).eq(house.getName()).
                  and(houseSQV.getId()).notEq(house.getId());

        query.whereExists(houseSQ);

        validate(" from House hobj1 where exists (select 1 from House hobj2 where hobj2.name = hobj1.name and hobj2.id <> hobj1.id)");
    }

    @Test(expected=IllegalStateException.class)
    public void whereSubqueryRequiresSelectWhenNotUsedForExists() {
        House house = query.from(House.class);

        TypeSafeSubQuery<Style> houseSQ = query.subquery(Style.class);
        House houseSQV = houseSQ.from(House.class);
        houseSQ.where(houseSQV.getId()).notEq(house.getId());

        query.whereEnum(houseSQ).eq(Style.the1980s);
        // validated during toHqlQuery.
        query.toHqlQuery();
    }

    @Test
    public void selectSubQueryExists() {
        House house = query.from(House.class);

        TypeSafeSubQuery<Style> houseSQ = query.subquery(Style.class);
        House houseSQV = houseSQ.from(House.class);
        houseSQ.where(houseSQV.getName()).eq(house.getName()).
                  and(houseSQV.getId()).notEq(house.getId());

        query.selectValue(houseSQ.selectExists());

        validate("select (case when (exists (select 1 from House hobj2 where hobj2.name = hobj1.name and hobj2.id <> hobj1.id)) then true else false end) from House hobj1");
    }

    @Test
    public void selectSubQueryCount() {
        House house = query.from(House.class);

        TypeSafeSubQuery<Style> houseSQ = query.subquery(Style.class);
        House houseSQV = houseSQ.from(House.class);
        houseSQ.where(houseSQV.getName()).eq(house.getName()).
                  and(houseSQV.getId()).notEq(house.getId());

        query.selectValue(houseSQ.selectCount());

        validate("select (select count(*) from House hobj2 where hobj2.name = hobj1.name and hobj2.id <> hobj1.id) from House hobj1");
    }

    @Test
    public void selectSubqueryCountDistinct() {
        House house = query.from(House.class);

        TypeSafeSubQuery<Style> houseSQ = query.subquery(Style.class);
        House houseSQV = houseSQ.from(House.class);
        houseSQ.where(houseSQV.getName()).eq(house.getName()).
                  and(houseSQV.getId()).notEq(house.getId());

        query.selectValue(houseSQ.selectCountDistinct(houseSQV.getStyle()));

        validate("select (select count(distinct hobj2.style) from House hobj2 where hobj2.name = hobj1.name and hobj2.id <> hobj1.id) from House hobj1");
    }

    @Test
    public void whereGroupMultiOrTest() {
        House house = query.from(House.class);

        RestrictionsGroup group = query.getGroupedRestrictionsBuilder().createRestrictionsGroup();
        group.where(house.getStyle()).eq(Style.the1980s).
                 or(house.getFloors()).gt(2);

        query.where(group).and(house.getName()).startsWith("Castle");

        validate(" from House hobj1 where (hobj1.style = :np1 or hobj1.floors > :np2) and hobj1.name like :np3",
                Style.the1980s, 2, "Castle%");
    }

    @Test
    public void whereGroupMultiOrTestImproved() {
        RestrictionsGroupFactory rb = query.getGroupedRestrictionsBuilder();
        House house = query.from(House.class);

        query.and(
            rb.or(
                rb.where(house.getStyle()).eq(Style.the1980s),
                rb.where(house.getFloors()).gt(2)),
            rb.where(house.getName()).startsWith("Castle"));

        validate(" from House hobj1 where (hobj1.style = :np1 or hobj1.floors > :np2) and hobj1.name like :np3",
                Style.the1980s, 2, "Castle%");
    }

    /**
     * query.where, query.and, query.or all add the items directly to the query,
     * they should not be nested.
     */
    @Test(expected=IllegalArgumentException.class)
    public void wherNestedWithSelf() {
        House house = query.from(House.class);
        query.and(query.where(house.getFloors()).gt(2));
    }

    @Test
    public void testInRequiresBrackets() {
        RestrictionsGroupFactory rb = query.getGroupedRestrictionsBuilder();
        Person person = query.from(Person.class);

        List<String> names = Arrays.asList("Jos", "Marie", "Katrien");
        query.and(
            rb.where(person.getId()).eq(1L),
            rb.or(
                rb.where(person.getSex()).eq(Sex.Male),
                rb.where(person.getName()).in(names)
            )
        );
        validate(" from Person hobj1 where hobj1.id = :np1 and (hobj1.sex = :np2 or hobj1.name in (:np3))", 1L, Sex.Male, names);
    }
}

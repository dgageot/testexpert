/*
 * This file is part of TestExpert.
 *
 * Copyright (C) 2012
 * "David Gageot" <david@gageot.net>,
 *
 * TestExpert is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TestExpert is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TestExpert. If not, see <http://www.gnu.org/licenses/>.
 */
package net.gageot.test.assertions;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.fest.assertions.Condition;

public class Conditions {
  private Conditions() {
    // static class
  }

  public static <T, V> Condition<T> isEqual(final Function<? super T, V> transform, final V expectedValue) {
    return new Condition<T>() {
      @Override
      public boolean matches(T object) {
        return Objects.equal(transform.apply(object), expectedValue);
      }
    };
  }

  public static Condition<Object> reflectionEqualTo(final Object expected) {
    return new Condition<Object>() {
      @Override
      public boolean matches(Object actual) {
        return EqualsBuilder.reflectionEquals(expected, actual);
      }
    };
  }
}

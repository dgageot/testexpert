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

import org.junit.Test;

import static net.gageot.test.assertions.Conditions.reflectionEqualTo;
import static org.fest.assertions.Assertions.assertThat;

public class ConditionsTest {
  @Test
  public void should_compare_using_reflection() {
    Bean bean1 = new Bean("key1", "value1");
    Bean bean2 = new Bean("key2", "value2");

    assertThat(bean1).is(reflectionEqualTo(bean1));
    assertThat(bean2).is(reflectionEqualTo(bean2));
    assertThat(bean1).isNot(reflectionEqualTo(bean2));
    assertThat(bean2).isNot(reflectionEqualTo(bean1));
    assertThat(bean1).isNot(reflectionEqualTo(null));
    assertThat(bean2).isNot(reflectionEqualTo(null));
    assertThat(bean1).isNot(reflectionEqualTo(new Object()));
    assertThat(bean2).isNot(reflectionEqualTo(new Object()));
    assertThat(bean1).isNot(reflectionEqualTo(new Bean("key1", "value2")));
    assertThat(bean1).isNot(reflectionEqualTo(new Bean("key2", "value1")));
    assertThat(bean1).isNot(reflectionEqualTo(new Bean("", "")));
  }

  static final class Bean {
    final String key;
    final String value;

    Bean(String key, String value) {
      this.key = key;
      this.value = value;
    }
  }
}

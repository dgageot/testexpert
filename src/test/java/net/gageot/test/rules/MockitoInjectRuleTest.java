package net.gageot.test.rules;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class MockitoInjectRuleTest {

    class Service {
	Helper helper = new Helper();

	String make() {
	    return helper.help();
	}
    }

    class Helper {
	String help() {
	    return "real";
	}
    }

    @InjectMocks
    Service service = new Service();

    @Mock
    Helper helper;

    @Rule
    public TestRule mockito = new MockitoInjectRule(this);

    @Test
    public void testRule() throws Exception {

	when(helper.help()).thenReturn("mock");

	String result = service.make();
	{
	    assertThat(result).isEqualTo("mock");
	}
    }
}

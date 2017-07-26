package br.edu.ifrn.tads.seguranca;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Test;

@SpringApplicationConfiguration(classes = TrabalhoFinalApplication.class)
@WebAppConfiguration
public class TrabalhoFinalApplicationIT extends AbstractTestNGSpringContextTests
{
	@Test
	public void contextLoads()
	{ }
}

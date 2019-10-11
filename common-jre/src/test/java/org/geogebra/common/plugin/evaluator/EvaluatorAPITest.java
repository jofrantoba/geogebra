package org.geogebra.common.plugin.evaluator;

import static org.junit.Assert.assertEquals;

import org.geogebra.common.BaseUnitTest;
import org.geogebra.common.io.EditorTyper;
import org.geogebra.common.io.MathFieldCommon;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * Tests for the EvaluatorAPI
 */
public class EvaluatorAPITest extends BaseUnitTest {

	private EditorTyper typer;
	private EvaluatorAPI api;

	@Before
	public void setupTest() {
		MathFieldCommon mathField = new MathFieldCommon();
		api = new EvaluatorAPI(getKernel(), mathField.getInternal());
		typer = new EditorTyper(mathField);
	}

	@Test
	public void testGetEvaluatorValue() {
		typer.type("1/2");
		Map<String, Object> value = api.getEvaluatorValue();

		assertEquals("{\\frac{1}{2}}", value.get("latex").toString());
		assertEquals("(1)/(2)", value.get("content").toString());
		assertEquals("0.5", value.get("eval").toString());
	}

	@Test
	public void testGetEvaluatorValueNonNumeric() {
		typer.type("GeoGebra");
		Map<String, Object> value = api.getEvaluatorValue();

		assertEquals("GeoGebra", value.get("latex").toString());
		assertEquals("GeoGebra", value.get("content").toString());
		assertEquals("NaN", value.get("eval").toString());
	}

	@Test
	public void testEmptyInput() {
		Map<String, Object> value = api.getEvaluatorValue();

		assertEquals("\\nbsp ", value.get("latex").toString());
		assertEquals("", value.get("content").toString());
		assertEquals("NaN", value.get("eval").toString());
	}

	@Test
	public void testInvalidInput() {
		typer.type("1/");
		Map<String, Object> value = api.getEvaluatorValue();

		assertEquals("{\\frac{1}{\\nbsp }}", value.get("latex").toString());
		assertEquals("(1)/()", value.get("content").toString());
		assertEquals("NaN", value.get("eval").toString());
	}
}

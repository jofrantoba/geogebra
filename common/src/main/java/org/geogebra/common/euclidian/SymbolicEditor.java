package org.geogebra.common.euclidian;

import org.geogebra.common.awt.GGraphics2D;
import org.geogebra.common.awt.GPoint;
import org.geogebra.common.awt.GRectangle;
import org.geogebra.common.euclidian.draw.DrawInputBox;
import org.geogebra.common.kernel.geos.GeoElement;
import org.geogebra.common.kernel.geos.GeoInputBox;
import org.geogebra.common.main.App;
import org.geogebra.common.util.SyntaxAdapterImpl;

import com.himamis.retex.editor.share.editor.MathFieldInternal;
import com.himamis.retex.editor.share.event.MathFieldListener;
import com.himamis.retex.editor.share.model.MathFormula;
import com.himamis.retex.editor.share.model.MathSequence;
import com.himamis.retex.editor.share.serializer.TeXSerializer;

/**
 * MathField-capable editor for input boxes on EuclidianView.
 */
public abstract class SymbolicEditor implements MathFieldListener {

	protected final App app;
	protected final EuclidianView view;

	protected TeXSerializer serializer;

	private GeoInputBox geoInputBox;
	private DrawInputBox drawInputBox;

	protected SymbolicEditor(App app, EuclidianView view) {
		this.app = app;
		this.view = view;
		this.serializer = new TeXSerializer(new SyntaxAdapterImpl(app.getKernel()));
	}

	protected void applyChanges() {
		setTempUserDisplayInput();
		String editedText = getMathFieldInternal().getText();
		geoInputBox.updateLinkedGeo(editedText);
	}

	protected void setTempUserDisplayInput() {
		MathFormula formula = getMathFieldInternal().getFormula();
		String latex = serializer.serialize(formula);
		geoInputBox.setTempUserDisplayInput(latex);
	}

	protected abstract MathFieldInternal getMathFieldInternal();

	/**
	 * Hide the editor if it was attached.
	 */
	public abstract void hide();

	/**
	 * @param point
	 *            mouse coordinates
	 * @return if editor is clicked.
	 */
	public abstract boolean isClicked(GPoint point);

	/**
	 * Attach the symbolic editor to the specified input box for editing it.
	 *
	 * @param geoInputBox
	 *            GeoInputBox to edit.
	 *
	 * @param bounds
	 *            place to attach the editor to.
	 */
	public abstract void attach(GeoInputBox geoInputBox, GRectangle bounds);

	protected void setInputBox(GeoInputBox geoInputBox) {
		this.geoInputBox = geoInputBox;
		this.drawInputBox = (DrawInputBox) view.getDrawableFor(geoInputBox);
	}

	@Override
	public void onCursorMove() {
		// nothing to do.
	}

	@Override
	public void onUpKeyPressed() {
		// nothing to do.
	}

	@Override
	public void onDownKeyPressed() {
		// nothing to do.
	}

	@Override
	public String serialize(MathSequence selectionText) {
		return null;
	}

	@Override
	public void onInsertString() {
		// nothing to do.
	}

	@Override
	public void onEnter() {
		String oldLabel = getGeoInputBox().getLabelSimple();
		applyChanges();
		GeoElement input = getGeoInputBox().getKernel().lookupLabel(oldLabel);
		if (input == getGeoInputBox()) {
			resetChanges();
		} else {
			DrawableND drawable = view.getDrawableFor(input);
			if (drawable instanceof DrawInputBox) {
				showRedefinedBox(((DrawInputBox) drawable));
			}
		}
	}

	/**
	 * Show this for the new drawbale after redefine; overriden to be async in desktop
	 * @param drawable input box drawable
	 */
	protected void showRedefinedBox(DrawInputBox drawable) {
		drawable.setWidgetVisible(true);
	}

	protected abstract void resetChanges();

	public abstract void repaintBox(GGraphics2D g2);

	public GeoInputBox getGeoInputBox() {
		return geoInputBox;
	}

	public DrawInputBox getDrawInputBox() {
		return drawInputBox;
	}
}

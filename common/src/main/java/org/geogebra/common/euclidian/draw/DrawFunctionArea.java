package org.geogebra.common.euclidian.draw;

import org.geogebra.common.euclidian.Drawable;
import org.geogebra.common.kernel.arithmetic.Command;
import org.geogebra.common.kernel.arithmetic.ExpressionValue;
import org.geogebra.common.kernel.arithmetic.Function;
import org.geogebra.common.kernel.arithmetic.MyDouble;
import org.geogebra.common.kernel.arithmetic.NumberValue;
import org.geogebra.common.kernel.geos.GeoCasCell;
import org.geogebra.common.kernel.geos.GeoFunction;

public abstract class DrawFunctionArea extends Drawable {
	protected GeoFunction asFunction(Command cmd, int i) {
		ExpressionValue arg0 = cmd.getArgument(i).unwrap();
		if (arg0 instanceof GeoCasCell) {
			// https://help.geogebra.org/topic/integraaltussen-wordt-grafisch-verkeerd-weergegeven-via-cas
			return (GeoFunction) ((GeoCasCell) arg0).getTwinGeo();
		}
		return new GeoFunction(
				view.getApplication().getKernel().getConstruction(),
				new Function(cmd.getArgument(i).wrap().replaceCasCommands()));
	}

	protected NumberValue asDouble(Command cmd, int i) {
		ExpressionValue arg2 = cmd.getArgument(i).unwrap();
		if (arg2 instanceof GeoCasCell) {
			return new MyDouble(cmd.getKernel(),
					((GeoCasCell) arg2).getTwinGeo().evaluateDouble());
		}
		return new MyDouble(cmd.getKernel(), cmd.getArgument(i).wrap()
				.replaceCasCommands().evaluateDouble());
	}
}

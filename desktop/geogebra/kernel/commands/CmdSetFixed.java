package geogebra.kernel.commands;

import geogebra.common.main.MyError;
import geogebra.kernel.Kernel;
import geogebra.kernel.arithmetic.Command;
import geogebra.kernel.geos.GeoBoolean;
import geogebra.kernel.geos.GeoElement;

/**
 *SetFixed
 */
class CmdSetFixed extends CmdScripting {

	/**
	 * Create new command processor
	 * 
	 * @param kernel
	 *            kernel
	 */
	public CmdSetFixed(Kernel kernel) {
		super(kernel);
	}

	final public void perform(Command c) throws MyError {
		int n = c.getArgumentNumber();
		GeoElement[] arg;

		switch (n) {
		case 2:
			arg = resArgs(c);
			if (arg[1].isGeoBoolean()) {

				GeoElement geo = (GeoElement) arg[0];

				geo.setFixed(((GeoBoolean) arg[1]).getBoolean());
				geo.updateRepaint();

				
				return;
			} else
				throw argErr(app, c.getName(), arg[1]);

		default:
			throw argNumErr(app, c.getName(), n);
		}
	}
}

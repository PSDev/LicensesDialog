package de.psdev.licensesdialog.licenses;

import android.content.Context;
import de.psdev.licensesdialog.R;

public class BSD3ClauseLicense extends License {

	private static final long serialVersionUID = -5205394619884057474L;

	@Override
	public String getName() {
		return "BSD 3-Clause License";
	}

	@Override
	public String getSummaryText(final Context context) {
		return getContent(context, R.raw.bsd3_summary);
	}

	@Override
	public String getFullText(final Context context) {
		return getContent(context, R.raw.bsd3_full);
	}

	@Override
	public String getVersion() {
		return "";
	}

	@Override
	public String getUrl() {
		return "http://opensource.org/licenses/BSD-3-Clause";
	}

}

package filters;

import play.http.HttpFilters;
import play.mvc.EssentialFilter;

import javax.inject.Inject;

public class Filters implements HttpFilters {


    private LoggingFilter traceFilter;

    @Inject
    public Filters(LoggingFilter traceFilter) {
        this.traceFilter = traceFilter;
    }

    @Override
    public EssentialFilter[] filters() {
        return new EssentialFilter[] { traceFilter };
    }
}

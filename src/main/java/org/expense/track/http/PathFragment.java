package org.expense.track.http;

/**
 * Provides methods to split up a URI into individual fragments.
 */
class PathFragment {

    // Holds the entire pathInfo String
    private String pathInfo;

    // Holds the individual parts of the pathInfo String
    private String[] pathFragments;

    static final int PATH_ROOT = 0;
    static final int PATH_TWO = 2;
    static final int PATH_THREE = 3;
    static final int PATH_FOUR = 4;

    /**
     * Creates a new PathFragment.
     */
    PathFragment(String pathInfo) {
        this.pathInfo = pathInfo;
        if (this.pathInfo != null)
            this.pathFragments = pathInfo.split("/");
    }

    /**
     * Returns the first path fragment. Given /path1/path2/, returns "path2".
     */
    String getLastFragment() {
        String result = "";
        if (hasFragments()) {
            result = pathFragments[pathFragments.length - 1];
        }
        return result;
    }

    /**
     * Returns the given path fragment by index. Given /path1/path2/, and i = 0,
     * returns "path1".
     */
    String getFragment(int i) {
        String result = "";
        if (hasFragments() && i >= 0 && i < pathFragments.length) {
            result = pathFragments[i];
        }
        return result;
    }

    /**
     * Returns the total number of path fragments. Given /path1/path2/, returns
     * 2.
     */
    int getFragmentCount() {
        if (hasFragments()) {
            return pathFragments.length;
        }
        return 0;
    }

    private boolean hasFragments() {
        return this.pathInfo != null && pathFragments != null;
    }

}

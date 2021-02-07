package net.draal.home.hs1xx.testutil

import us.abstracta.wiresham.Flow

class SimpleFlow {

    private SimpleFlow() {
    }

    public static Flow getFlow(URI resource) throws FileNotFoundException {
        return Flow.fromYml(new File(resource))
    }
}

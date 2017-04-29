package view

/**
 * Created by chist on 2/18/17.
 */
enum LabType {
    NEWTON_POLYNOMIAL("Newton polynomial", 0){
        @Override
        void createAndShow() {
            new NewtonPolynomialFrame().setVisible true
        }
    },
    POISSON_DISTRIBUTION("Poisson distribution", 1){
        @Override
        void createAndShow() {
            new PoissonDistributionFrame().setVisible true
        }
    },
    PRNG("Pseudorandom number generator", 2){
        @Override
        void createAndShow() {
            new PRNGFrame().setVisible true
        }
    },
    STATE_GRAPH('State graph', 3){
        @Override
        protected void createAndShow() {
            new StateGraphFrame().setVisible true
        }
    },
    SINGLE_LINE('Single line', 4){
        @Override
        protected void createAndShow() {
            new SingleLineFrame().setVisible true
        }
    },
    MULTIPLE_LINE('Multiple line', 5){
        @Override
        protected void createAndShow() {
            new MultipleLineFrame().setVisible true
        }
    }

    String labName
    int id

    LabType(labName, id){
        this.labName = labName
        this.id = id
    }

    static void showFrameById(int id){
        values().each {
            if(it.id == id){
                it.createAndShow()
            }
        }
    }

    protected void createAndShow() {}
}
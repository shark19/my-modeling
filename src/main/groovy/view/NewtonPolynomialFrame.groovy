package view
/**
 * Created by chist on 2/18/17.
 */
class NewtonPolynomialFrame extends CommonFrame{

    @Override
    protected void createContent(){
        //todo ui
    }

    @Override
    protected void fillProps() {
        super.fillProps()
        title = LabType.NEWTON_POLYNOMIAL.getProperty("labName") as String
    }
}

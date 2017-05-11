package transportation;

import util.GLPKUtil;
import org.gnu.glpk.*;

// Minimize z = 20x11 + 16x12 + 24x13 + 
//              10x21 + 10x22 +  8x23 + 
//              12x31 + 18x32 + 10x33
// subject to
// -x11 - x12 - x13 >= -300
// -x21 - x22 - x23 >= -500
// -x31 - x32 - x33 >= -200
// x11 + x21 + x31 >= 200
// x12 + x22 + x32 >= 400
// x13 + x23 + x33 >= 300
// where,
// 0 <= x11
// 0 <= x12
// 0 <= x13
// 0 <= x21
// 0 <= x22
// 0 <= x23
// 0 <= x31
// 0 <= x32
// 0 <= x33
//
// offer = 1000
// demand = 900

public class Wine {

    public static void main(String[] arg) {
        glp_prob lp;
        glp_smcp parm;
        SWIGTYPE_p_int ind;
        SWIGTYPE_p_double val;
        int ret;

        int numberVariables = 9;
        int numberConstraints = 6;
        String[] variables = {"x11", "x12", "x13",
                "x21", "x22", "x23",
                "x31", "x32", "x33"};
        double[][] constraints = {{-1,-1,-1,0,0,0,0,0,0},
                {0,0,0,-1,-1,-1,0,0,0},
                {0,0,0,0,0,0,-1,-1,-1},
                {1,0,0,1,0,0,1,0,0},
                {0,1,0,0,1,0,0,1,0},
                {0,0,1,0,0,1,0,0,1}};
        double[] bounds = {-300,-500,-200,200,400,300};
        double[] coefficients = {20,16,24,10,10,8,12,18,10};

        try {
            // Create problem
            lp = GLPK.glp_create_prob();
            System.out.println("Problem created");
            GLPK.glp_set_prob_name(lp, "Transportation 7 - Wine");

            // Define columns
            GLPK.glp_add_cols(lp, numberVariables); // number of variables

            for (int i = 1; i <= numberVariables; i++) {
                GLPK.glp_set_col_name(lp, i, variables[i-1]);
                GLPK.glp_set_col_kind(lp, i, GLPKConstants.GLP_IV);
                GLPK.glp_set_col_bnds(lp, i, GLPKConstants.GLP_LO, 0, 0);
            }

            // Create constraints

            // Allocate memory
            ind = GLPK.new_intArray(numberVariables);
            val = GLPK.new_doubleArray(numberVariables);

            // Create rows
            GLPK.glp_add_rows(lp, numberConstraints);

            // Set row details
            GLPKUtil.set_lo_constraints(lp, ind, val, bounds,constraints, 1, numberConstraints, numberVariables);

            // Free memory
            GLPK.delete_intArray(ind);
            GLPK.delete_doubleArray(val);

            // Define objective
            GLPK.glp_set_obj_name(lp, "z");
            GLPK.glp_set_obj_dir(lp, GLPKConstants.GLP_MIN);
            GLPK.glp_set_obj_coef(lp, 0, 0);

            for (int i = 1; i <= numberVariables; i++) {
                GLPK.glp_set_obj_coef(lp, i, coefficients[i-1]);
            }

            // Write model to file
            GLPK.glp_write_lp(lp, null, "transportation7.lp");

            // Solve model
            parm = new glp_smcp();
            GLPK.glp_init_smcp(parm);
            ret = GLPK.glp_simplex(lp, parm);

            // Retrieve solution
            if (ret == 0) {
                GLPKUtil.write_lp_solution(lp,"transportation7.sol");
                //GLPK.glp_write_sol(lp, "transportation7.sol");
            } else {
                System.out.println("The problem could not be solved");
            }

            // Free memory
            GLPK.glp_delete_prob(lp);
        } catch (GlpkException ex) {
            ex.printStackTrace();
        }
    }
}

package path;

import org.gnu.glpk.*;
import util.GLPKUtil;

// Minimize z = 6000x12 + 2000x13 + 5000x14 +
//              9000x25 + 
//              3000x34 +
//              8000x45
// subject to
// -x12 - x13 - x14 = -5
// x12 - x25 = 0
// x13 - x34 = 0
// x14 + x34 - x45 = 0
// x25 + x45 = 5
// where,
// 0 <= x12
// 0 <= x13
// 0 <= x14
// 0 <= x25
// 0 <= x34
// 0 <= x45
//
// offer = 5
// demand = 5

public class Industry {

    public static void main(String[] arg) {
        glp_prob lp;
        glp_smcp parm;
        SWIGTYPE_p_int ind;
        SWIGTYPE_p_double val;
        int ret;

        int numberVariables = 6;
        int numberConstraints = 5;
        String[] variables = {"x12", "x13", "x14", "x25", "x34", "x45"};
        double[][] constraints = {{-1,-1,-1,0,0,0},
                {1,0,0,-1,0,0},
                {0,1,0,0,-1,0},
                {0,0,1,0,1,-1},
                {0,0,0,1,0,1}};
        double[] bounds = {-5,0,0,0,5};
        double[] coefficients = {6000,2000,5000,9000,3000,8000};

        try {
            // Create problem
            lp = GLPK.glp_create_prob();
            System.out.println("Problem created");
            GLPK.glp_set_prob_name(lp, "Path 10 - Industry");

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
            GLPKUtil.set_fx_constraints(lp, ind, val, bounds, constraints, 1, numberConstraints, numberVariables);

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
            GLPK.glp_write_lp(lp, null, "path10.lp");

            // Solve model
            parm = new glp_smcp();
            GLPK.glp_init_smcp(parm);
            ret = GLPK.glp_simplex(lp, parm);

            // Retrieve solution
            if (ret == 0) {
                GLPKUtil.write_lp_solution(lp,"path10.sol");
                //GLPK.glp_write_sol(lp, "path10.sol");
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

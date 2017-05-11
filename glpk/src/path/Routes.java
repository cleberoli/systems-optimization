package path;

import org.gnu.glpk.*;
import util.GLPKUtil;

// Minimize z = 4x13 + 4.5x14 + 9x15 + 8x16 + 
//              8x23 + 8x24 + 8x25 + 7x26 + 
//              1x34 + 6x35 + 5.5x36 +
//              6x45 + 5x46
// subject to
// -x13 - x14 - x15 - x16 >= -100
// -x23 - x24 - x25 - x26 >= -200
// x13 + x23 - x34 - x35 - x36 >= 0
// x14 + x24 + x34 - x45 - x46 >= 0
// x15 + x25 + x35 + x45 >= 150
// x16 + x26 + x36 + x46 >= 100
// where,
// 0 <= x13
// 0 <= x14
// 0 <= x15
// 0 <= x16
// 0 <= x23
// 0 <= x24
// 0 <= x25
// 0 <= x26
// 0 <= x34
// 0 <= x35
// 0 <= x36
// 0 <= x45
// 0 <= x46
//
// offer = 300
// demand = 250

public class Routes {

    public static void main(String[] arg) {
        glp_prob lp;
        glp_smcp parm;
        SWIGTYPE_p_int ind;
        SWIGTYPE_p_double val;
        int ret;

        int numberVariables = 13;
        int numberConstraints = 6;
        String[] variables = {"x13", "x14", "x15", "x16", "x23", "x24", "x25", "x26", "x34", "x35", "x36", "x45", "x46"};
        double[][] constraints = {{-1,-1,-1,-1,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,-1,-1,-1,-1,0,0,0,0,0},
                {1,0,0,0,1,0,0,0,-1,-1,-1,0,0},
                {0,1,0,0,0,1,0,0,1,0,0,-1,-1},
                {0,0,1,0,0,0,1,0,0,1,0,1,0},
                {0,0,0,1,0,0,0,1,0,0,1,0,1}};
        double[] bounds = {-100,-200,0,0,150,100};
        double[] coefficients = {4,4.5,9,8,8,8,8,7,1,6,5.5,6,5};

        try {
            // Create problem
            lp = GLPK.glp_create_prob();
            System.out.println("Problem created");
            GLPK.glp_set_prob_name(lp, "Path 6 - Routes");

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
            GLPKUtil.set_lo_constraints(lp, ind, val, bounds, constraints, 1, numberConstraints, numberVariables);

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
            GLPK.glp_write_lp(lp, null, "path6.lp");

            // Solve model
            parm = new glp_smcp();
            GLPK.glp_init_smcp(parm);
            ret = GLPK.glp_simplex(lp, parm);

            // Retrieve solution
            if (ret == 0) {
                GLPKUtil.write_lp_solution(lp,"path6.sol");
                //GLPK.glp_write_sol(lp, "path6.sol");
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

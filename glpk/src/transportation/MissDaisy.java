package transportation;

import util.GLPKUtil;
import org.gnu.glpk.*;

// Minimize z = 7x11 + 9x12 + 1x13 + 12x14 + 7x15 + 4x16 + 
//              4x21 + 5x22 + 12x23 + 1x24 + 3x25 + 8x26
// subject to
// -x11 - x12 - x13 - x14 - x15 - x16 <= -2500
// -x21 - x22 - x23 - x24 - x25 - x26 <= -2000
// x11 + x21 <= 1400
// x12 + x22 <= 1560
// x13 + x23 <= 400
// x14 + x24 <= 150
// x15 + x25 <= 870
// x16 + x26 <= 620
// where,
// 0 <= x11
// 0 <= x12
// 0 <= x13
// 0 <= x14
// 0 <= x15
// 0 <= x16
// 0 <= x21
// 0 <= x22
// 0 <= x23
// 0 <= x24
// 0 <= x25
// 0 <= x26
//
// offer = 4500
// demand = 5000

public class MissDaisy {
    
    public static void main(String[] arg) {
        glp_prob lp;
        glp_smcp parm;
        SWIGTYPE_p_int ind;
        SWIGTYPE_p_double val;
        int ret;

        int numberVariables = 12;
        int numberConstraints = 8;
        String[] variables = {"x11", "x12", "x13", "x14", "x15", "x16",
                "x21", "x22", "x23", "x24", "x25", "x26"};
        double[][] constraints = {{-1,-1,-1,-1,-1,-1,0,0,0,0,0,0},
                {0,0,0,0,0,0,-1,-1,-1,-1,-1,-1},
                {1,0,0,0,0,0,1,0,0,0,0,0},
                {0,1,0,0,0,0,0,1,0,0,0,0},
                {0,0,1,0,0,0,0,0,1,0,0,0},
                {0,0,0,1,0,0,0,0,0,1,0,0},
                {0,0,0,0,1,0,0,0,0,0,1,0},
                {0,0,0,0,0,1,0,0,0,0,0,1}};
        double[] bounds = {-2500,-2000,1400,1560,400,150,870,620};
        double[] coefficients = {7,9,1,12,7,4,4,5,12,1,3,8};

        try {
            // Create problem
            lp = GLPK.glp_create_prob();
            System.out.println("Problem created");
            GLPK.glp_set_prob_name(lp, "Transportation 1 - Miss Daisy");

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
            GLPKUtil.set_up_constraints(lp, ind, val, bounds, constraints, 1, numberConstraints, numberVariables);

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
            GLPK.glp_write_lp(lp, null, "transportation1.lp");

            // Solve model
            parm = new glp_smcp();
            GLPK.glp_init_smcp(parm);
            ret = GLPK.glp_simplex(lp, parm);

            // Retrieve solution
            if (ret == 0) {
                GLPKUtil.write_lp_solution(lp,"transportation1.sol");
                //GLPK.glp_write_sol(lp, "transportation1.sol");
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

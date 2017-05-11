package transportation;

import util.GLPKUtil;
import org.gnu.glpk.*;

// Minimize z = 14x11 +  5x12 + 8x13 +  7x14 + 9x15 + 
//               2x21 + 12x22 + 6x23 +  5x24 + 7x25 + 
//               7x31 +  8x32 + 3x33 +  9x34 + 7x35 + 
//               2x41 +  4x42 + 6x43 + 10x44 + 6x45 
// subject to
// -x11 - x12 - x13 - x14 - x15 <= -1
// -x21 - x22 - x23 - x24 - x25 <= -1
// -x31 - x32 - x33 - x34 - x35 <= -1
// -x41 - x42 - x43 - x44 - x45 <= -1
// x11 + x21 + x31 + x41 <= 1
// x12 + x22 + x32 + x42 <= 1
// x13 + x23 + x33 + x43 <= 1
// x14 + x24 + x34 + x44 <= 1
// x15 + x25 + x35 + x45 <= 1
// where,
// 0 <= x11
// 0 <= x12
// 0 <= x13
// 0 <= x14
// 0 <= x15
// 0 <= x21
// 0 <= x22
// 0 <= x23
// 0 <= x24
// 0 <= x25
// 0 <= x31
// 0 <= x32
// 0 <= x33
// 0 <= x34
// 0 <= x35
// 0 <= x41
// 0 <= x42
// 0 <= x43
// 0 <= x44
// 0 <= x45
//
// offer = 4
// demand = 5
public class SuperMachine {

    public static void main(String[] arg) {
        glp_prob lp;
        glp_smcp parm;
        SWIGTYPE_p_int ind;
        SWIGTYPE_p_double val;
        int ret;

        int numberVariables = 20;
        int numberConstraints = 9;
        String[] variables = {"x11", "x12", "x13","x14", "x15",
                "x21", "x22", "x23","x24", "x25",
                "x31", "x32", "x33","x34", "x35",
                "x41", "x42", "x43","x44", "x45"};
        double[][] constraints = {{-1,-1,-1,-1,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,-1,-1,-1,-1,-1,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,-1,-1,-1,-1,-1,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,-1,-1},
                {1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0},
                {0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0},
                {0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0},
                {0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0},
                {0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1}};
        double[] bounds = {-2,-2,-2,-2,1,1,1,1,1};
        double[] coefficients = {14,5,8,7,9,2,12,6,5,7,7,8,3,9,7,2,4,6,10,6};

        try {
            // Create problem
            lp = GLPK.glp_create_prob();
            System.out.println("Problem created");
            GLPK.glp_set_prob_name(lp, "Transportation 8 - SuperMachine");

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
            GLPK.glp_write_lp(lp, null, "transportation8b.lp");

            // Solve model
            parm = new glp_smcp();
            GLPK.glp_init_smcp(parm);
            ret = GLPK.glp_simplex(lp, parm);

            // Retrieve solution
            if (ret == 0) {
                GLPKUtil.write_lp_solution(lp,"transportation8b.sol");
                //GLPK.glp_write_sol(lp, "transportation8b.sol");
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

package path;

import util.GLPKUtil;
import org.gnu.glpk.*;

// Minimize z = 400x12 + 950x13 + 800x14 + 
//              1800x25 + 900x26 + 
//              1100x35 + 600x36 +
//              600x46 + 1200x47 +
//              400x58 + 
//              900x65 + 1000x67 + 1300x68
//              600x78
// subject to
// -x12 - x13 - x14 = -1
// x12 - x25 - x26 = 0
// x13 - x35 - x36 = 0
// x14 - x46 - x47 = 0
// x25 + x35 + x65 - x58 = 0
// x26 + x36 + x46 - x65 - x67 -x68 = 0
// x47 + x67 - x78 = 0
// x58 + x68 + x78 = 1
// where,
// 0 <= x12
// 0 <= x13
// 0 <= x14
// 0 <= x25
// 0 <= x26
// 0 <= x35
// 0 <= x36
// 0 <= x46
// 0 <= x47
// 0 <= x58
// 0 <= x65
// 0 <= x67
// 0 <= x68
// 0 <= x78
//
// offer = 1
// demand = 1

public class Roads {

    public static void main(String[] arg) {
        glp_prob lp;
        glp_smcp parm;
        SWIGTYPE_p_int ind;
        SWIGTYPE_p_double val;
        int ret;

        int numberVariables = 14;
        int numberConstraints = 8;
        String[] variables = {"x12", "x13", "x14", "x25", "x26", "x35", "x36", "x46", "x47", "x58", "x65", "x67", "x68", "x78"};
        double[][] constraints = {{-1,-1,-1,0,0,0,0,0,0,0,0,0,0,0},
                {1,0,0,-1,-1,0,0,0,0,0,0,0,0,0},
                {0,1,0,0,0,-1,-1,0,0,0,0,0,0,0},
                {0,0,1,0,0,0,0,0,-1,-1,0,0,0,0},
                {0,0,0,1,0,1,0,0,0,-1,1,0,0,0},
                {0,0,0,0,1,0,1,1,0,0,-1,-1,-1,0},
                {0,0,0,0,0,0,0,0,1,0,0,1,0,-1},
                {0,0,0,0,0,0,0,0,0,1,0,0,1,1}};
        double[] bounds = {-1,0,0,0,0,0,0,1};
        double[] coefficients = {400,950,800,1800,900,1100,600,600,1200,400,900,1000,1300,600};

        try {
            // Create problem
            lp = GLPK.glp_create_prob();
            System.out.println("Problem created");
            GLPK.glp_set_prob_name(lp, "Path 1 - Roads");

            // Define columns
            GLPK.glp_add_cols(lp, numberVariables); // number of variables

            for (int i = 1; i <= numberVariables; i++) {
                GLPK.glp_set_col_name(lp, i, variables[i-1]);
                GLPK.glp_set_col_kind(lp, i, GLPKConstants.GLP_BV);
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
            GLPK.glp_write_lp(lp, null, "path1.lp");

            // Solve model
            parm = new glp_smcp();
            GLPK.glp_init_smcp(parm);
            ret = GLPK.glp_simplex(lp, parm);

            // Retrieve solution
            if (ret == 0) {
                GLPKUtil.write_lp_solution(lp,"path1.sol");
                //GLPK.glp_write_sol(lp, "path1.sol");
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

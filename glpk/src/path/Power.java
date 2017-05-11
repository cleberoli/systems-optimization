package path;

import org.gnu.glpk.*;
import util.GLPKUtil;

// Maximize z = x81
// subject to
// x81 - x12 - x13 - x14 = 0
// x12 - x25 - x26 = 0
// x13 - x35 - x36 = 0
// x14 - x46 - x47 = 
// x25 + x35 + x65 - x58 = 0
// x26 + x36 + x46 - x65 - x67 -x68 = 0
// x47 + x67 - x78 = 0
// x58 + x68 + x78 - x81 = 0
// where,
// 0 <= x12 <= 400
// 0 <= x13 <= 950
// 0 <= x14 <= 800
// 0 <= x25 <= 1800
// 0 <= x26 <= 900
// 0 <= x35 <= 1100
// 0 <= x36 <= 600
// 0 <= x46 <= 600
// 0 <= x47 <= 1200
// 0 <= x58 <= 400
// 0 <= x65 <= 900
// 0 <= x67 <= 1000
// 0 <= x68 <= 1300
// 0 <= x78 <= 600
// 0 <= x81

public class Power {

    public static void main(String[] arg) {
        glp_prob lp;
        glp_smcp parm;
        SWIGTYPE_p_int ind;
        SWIGTYPE_p_double val;
        int ret;

        int numberVariables = 15;
        int numberConstraints = 8;
        String[] variables = {"x12", "x13", "x14", "x25", "x26", "x35", "x36", "x46", "x47", "x58", "x65", "x67", "x68", "x78", "x81"};
        double[][] constraints = {{-1,-1,-1,0,0,0,0,0,0,0,0,0,0,0,1},
                {1,0,0,-1,-1,0,0,0,0,0,0,0,0,0,0},
                {0,1,0,0,0,-1,-1,0,0,0,0,0,0,0,0},
                {0,0,1,0,0,0,0,0,-1,-1,0,0,0,0,0},
                {0,0,0,1,0,1,0,0,0,-1,1,0,0,0,0},
                {0,0,0,0,1,0,1,1,0,0,-1,-1,-1,0,0},
                {0,0,0,0,0,0,0,0,1,0,0,1,0,-1,0},
                {0,0,0,0,0,0,0,0,0,1,0,0,1,1,-1}};
        double[] bounds = {0,0,0,0,0,0,0,0};
        double[] coefficients = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1};
        double[] coefficientsBound = {400,950,800,1800,900,1100,600,600,1200,400,900,1000,1300,600,0};

        try {
            // Create problem
            lp = GLPK.glp_create_prob();
            System.out.println("Problem created");
            GLPK.glp_set_prob_name(lp, "Path 2 - Power");

            // Define columns
            GLPK.glp_add_cols(lp, numberVariables); // number of variables

            for (int i = 1; i < numberVariables; i++) {
                GLPK.glp_set_col_name(lp, i, variables[i-1]);
                GLPK.glp_set_col_kind(lp, i, GLPKConstants.GLP_BV);
                GLPK.glp_set_col_bnds(lp, i, GLPKConstants.GLP_DB, 0, coefficientsBound[i-1]);
            }

            GLPK.glp_set_col_name(lp, numberVariables, variables[numberVariables-1]);
            GLPK.glp_set_col_kind(lp, numberVariables, GLPKConstants.GLP_BV);
            GLPK.glp_set_col_bnds(lp, numberVariables, GLPKConstants.GLP_LO, 0, 0);

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
            GLPK.glp_set_obj_dir(lp, GLPKConstants.GLP_MAX);
            GLPK.glp_set_obj_coef(lp, 0, 0);

            for (int i = 1; i <= numberVariables; i++) {
                GLPK.glp_set_obj_coef(lp, i, coefficients[i-1]);
            }

            // Write model to file
            GLPK.glp_write_lp(lp, null, "path2.lp");

            // Solve model
            parm = new glp_smcp();
            GLPK.glp_init_smcp(parm);
            ret = GLPK.glp_simplex(lp, parm);

            // Retrieve solution
            if (ret == 0) {
                GLPKUtil.write_lp_solution(lp,"path2.sol");
                //GLPK.glp_write_sol(lp, "path2.sol");
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

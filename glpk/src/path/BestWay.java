package path;

import org.gnu.glpk.*;
import util.GLPKUtil;

// Maximize z = x61
// subject to
// x61 - x12 - x13 = 0
// x12 - x23 - x24 - x25 = 0
// x13 + x23 - x34 - x36 = 0
// x24 + x34 - x45 = 0 
// x25 + x45 - x56 = 0
// x36 + x56 - x61 = 0
// where,
// 0 <= x12 <= 65
// 0 <= x13 <= 60
// 0 <= x23 <= 95
// 0 <= x24 <= 45
// 0 <= x25 <= 15
// 0 <= x34 <= 35
// 0 <= x36 <= 90
// 0 <= x45 <= 40
// 0 <= x56 <= 30
// 0 <= x61

public class BestWay {

    public static void main(String[] arg) {
        glp_prob lp;
        glp_smcp parm;
        SWIGTYPE_p_int ind;
        SWIGTYPE_p_double val;
        int ret;

        int numberVariables = 10;
        int numberConstraints = 6;
        String[] variables = {"x12", "x13", "x23", "x24", "x25", "x34", "x36", "x45", "x56", "x61"};
        double[][] constraints = {{-1,-1,0,0,0,0,0,0,0,1},
                {1,0,-1,-1,-1,0,0,0,0,0},
                {0,1,1,0,0,-1,-1,0,0,0},
                {0,0,0,1,0,1,0,-1,0,0},
                {0,0,0,0,1,0,0,1,-1,0},
                {0,0,0,0,0,0,1,0,1,-1}};
        double[] bounds = {0,0,0,0,0,0};
        double[] coefficients = {0,0,0,0,0,0,0,0,0,1};
        double[] coefficientsBound = {65,60,95,45,15,35,90,40,30,0};

        try {
            // Create problem
            lp = GLPK.glp_create_prob();
            System.out.println("Problem created");
            GLPK.glp_set_prob_name(lp, "Path 7 - BestWay");

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
            GLPK.glp_write_lp(lp, null, "path7.lp");

            // Solve model
            parm = new glp_smcp();
            GLPK.glp_init_smcp(parm);
            ret = GLPK.glp_simplex(lp, parm);

            // Retrieve solution
            if (ret == 0) {
                GLPKUtil.write_lp_solution(lp,"path7.sol");
                //GLPK.glp_write_sol(lp, "path7.sol");
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

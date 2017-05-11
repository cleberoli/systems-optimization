package scales;

import org.gnu.glpk.*;
import util.GLPKUtil;

// Minimize z = 1.75x1 + 1.5x2 + 1.25x3 + x4 + x5 + x6 + x7 + x8 + 1.25x9 + 1.5x10 + 1.75x11
// subject to
// x10 + x11 + x1 >= 40
// x11 + x1 + x2 >= 30
// x1 + x2 + x3 >= 40
// x1 + x2 + x3 + x4 >= 60
// x2 + x3 + x4 + x5 >= 60
// x3 + x4 + x5 + x6 >= 70
// x4 + x5 + x6 + x7 >= 80
// x5 + x6 + x7 + x8 >= 80
// x6 + x7 + x8 + x9 >= 70
// x7 + x8 + x9 + x10 >= 70
// x8 + x9 + x10 + x11 >= 60
// x9 + x10 + x11 >= 50
// where,
// 0 <= x1
// 0 <= x2
// 0 <= x3
// 0 <= x4
// 0 <= x5
// 0 <= x6
// 0 <= x7
// 0 <= x8
// 0 <= x9
// 0 <= x10
// 0 <= x11

public class Hospital {

    public static void main(String[] arg) {
        glp_prob lp;
        glp_smcp parm;
        SWIGTYPE_p_int ind;
        SWIGTYPE_p_double val;
        int ret;

        int numberVariables = 11;
        int numberConstraints = 12;
        String[] variables = {"x1", "x2", "x3", "x4", "x5", "x6", "x7", "x8", "x9", "x10", "x11"};
        double[][] constraints = {{1,0,0,0,0,0,0,0,0,1,1},
                {1,1,0,0,0,0,0,0,0,0,1},
                {1,1,1,0,0,0,0,0,0,0,0},
                {1,1,1,1,0,0,0,0,0,0,0},
                {0,1,1,1,1,0,0,0,0,0,0},
                {0,0,1,1,1,1,0,0,0,0,0},
                {0,0,0,1,1,1,1,0,0,0,0},
                {0,0,0,0,1,1,1,1,0,0,0},
                {0,0,0,0,0,1,1,1,1,0,0},
                {0,0,0,0,0,0,1,1,1,1,0},
                {0,0,0,0,0,0,0,1,1,1,1},
                {0,0,0,0,0,0,0,0,1,1,1}};
        double[] bounds = {40,30,40,60,60,70,80,80,70,70,60,50};
        double[] coefficients = {1.75,1.5,1.25,1,1,1,1,1,1.25,1.5,1.75};

        try {
            // Create problem
            lp = GLPK.glp_create_prob();
            System.out.println("Problem created");
            GLPK.glp_set_prob_name(lp, "Scale 7 - Hospital");

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
            GLPKUtil.set_lo_constraints(lp, ind, val, bounds, constraints, 1, numberConstraints, numberVariables);

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
            GLPK.glp_write_lp(lp, null, "scale7.lp");

            // Solve model
            parm = new glp_smcp();
            GLPK.glp_init_smcp(parm);
            ret = GLPK.glp_simplex(lp, parm);

            // Retrieve solution
            if (ret == 0) {
                GLPKUtil.write_lp_solution(lp,"scale7.sol");
                //GLPK.glp_write_sol(lp, "scale7.sol");
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

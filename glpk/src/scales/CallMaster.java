package scales;

import org.gnu.glpk.*;
import util.GLPKUtil;

// Minimize z = x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9 + x10 + x11 + x12 + x13 + x14 + x15 + x16 + x17 + x18 + x19 + x20 + x21 + x22 + x23 + x24
// subject to
// x20 + x21 + x22 + x23 + x24 + x1 >= 29
// x21 + x22 + x23 + x24 + x1 + x2 >= 17
// x22 + x23 + x24 + x1 + x2 + x3 >= 9
// x23 + x24 + x1 + x2 + x3 + x4 >= 12
// x24 + x1 + x2 + x3 + x4 + x5 >= 26
// x1 + x2 + x3 + x4 + x5 + x6 >= 30
// x2 + x3 + x4 + x5 + x6 + x7 >= 54
// x3 + x4 + x5 + x6 + x7 + x8 >= 66
// x4 + x5 + x6 + x7 + x8 + x9 >= 83
// x5 + x6 + x7 + x8 + x9 + x10 >= 95
// x6 + x7 + x8 + x9 + x10 + x11 >= 77
// x7 + x8 + x9 + x10 + x11 + x12 >= 85
// x8 + x9 + x10 + x11 + x12 + x13 >= 88
// x9 + x10 + x11 + x12 + x13 + x14 >= 105
// x10 + x11 + x12 + x13 + x14 + x15 >= 114
// x11 + x12 + x13 + x14 + x15 + x16 >= 128
// x12 + x13 + x14 + x15 + x16 + x17 >= 114
// x13 + x14 + x15 + x16 + x17 + x18 >= 117
// x14 + x15 + x16 + x17 + x18 + x19 >= 102
// x15 + x16 + x17 + x18 + x19 + x20 >= 90
// x16 + x17 + x18 + x19 + x20 + x21 >= 81
// x17 + x18 + x19 + x20 + x21 + x22 >= 71
// x18 + x19 + x20 + x21 + x22 + x23 >= 57
// x19 + x20 + x21 + x22 + x23 + x24 >= 43
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
// 0 <= x12
// 0 <= x13
// 0 <= x14
// 0 <= x15
// 0 <= x16
// 0 <= x17
// 0 <= x18
// 0 <= x19
// 0 <= x20
// 0 <= x21
// 0 <= x22
// 0 <= x23
// 0 <= x24

public class CallMaster {

    public static void main(String[] arg) {
        glp_prob lp;
        glp_smcp parm;
        SWIGTYPE_p_int ind;
        SWIGTYPE_p_double val;
        int ret;

        int numberVariables = 24;
        int numberConstraints = 24;
        String[] variables = {"x1", "x2", "x3", "x4", "x5", "x6", "x7", "x8", "x9", "x10", "x11", "x12",
                "x13", "x14", "x15", "x16", "x17", "x18", "x19", "x20", "x21", "x22", "x23", "x24"};
        double[][] constraints = {{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1},
                {1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1},
                {1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1},
                {1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
                {1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                {1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1}};
        double[] bounds = {29,17,9,12,26,30,54,66,83,95,77,85,88,105,114,128,114,117,102,90,81,71,57,43};
        double[] coefficients = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};

        try {
            // Create problem
            lp = GLPK.glp_create_prob();
            System.out.println("Problem created");
            GLPK.glp_set_prob_name(lp, "Scale 6 - CallMaster");

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
            GLPK.glp_write_lp(lp, null, "scale6.lp");

            // Solve model
            parm = new glp_smcp();
            GLPK.glp_init_smcp(parm);
            ret = GLPK.glp_simplex(lp, parm);

            // Retrieve solution
            if (ret == 0) {
                GLPKUtil.write_lp_solution(lp,"scale6.sol");
                //GLPK.glp_write_sol(lp, "scale6.sol");
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

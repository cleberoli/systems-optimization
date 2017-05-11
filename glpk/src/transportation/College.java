package transportation;

import util.GLPKUtil;
import org.gnu.glpk.*;

// Minimize z = 10x11 + 21.1x12 + 25.0x13 + 26.4x14 + 17.1x15 + 20.0x16 + 21.3x17 +
//              20x21 + 17.0x22 + 13.5x23 + 14.0x24 + 25.0x25 + 25.3x26 + 22.3x27 +
//              14x31 + 40.0x32 + 20.0x33 + 13.0x34 + 12.0x35 + 15.0x36 + 25.0x37 +
//              17x41 + 20.0x42 + 15.0x43 + 20.0x44 + 20.0x45 + 22.0x46 + 19.0x47
// subject to
// -x11 - x12 - x13 - x14 - x15 - x16 - x17 >= -2
// -x21 - x22 - x23 - x24 - x25 - x26 - x27 >= -2
// -x31 - x32 - x33 - x34 - x35 - x36 - x37 >= -2
// -x41 - x42 - x43 - x44 - x45 - x46 - x47 >= -2
// x11 + x21 + x31 + x41 >= 1
// x12 + x22 + x32 + x42 >= 1
// x13 + x23 + x33 + x43 >= 1
// x14 + x24 + x34 + x44 >= 1
// x15 + x25 + x35 + x45 >= 1
// x16 + x26 + x36 + x46 >= 1
// x17 + x27 + x37 + x47 >= 1
// 10x11 + 21.1x12 + 25.0x13 + 26.4x14 + 17.1x15 + 20.0x16 + 21.3x17 <= 33
// 20x21 + 17.0x22 + 13.5x23 + 14.0x24 + 25.0x25 + 25.3x26 + 22.3x27 <= 33
// 14x31 + 40.0x32 + 20.0x33 + 13.0x34 + 12.0x35 + 15.0x36 + 25.0x37 <= 33
// 17x41 + 20.0x42 + 15.0x43 + 20.0x44 + 20.0x45 + 22.0x46 + 19.0x47 <= 33
// 10x11 + 21.1x12 + 25.0x13 + 26.4x14 + 17.1x15 + 20.0x16 + 21.3x17 +
// 20x21 + 17.0x22 + 13.5x23 + 14.0x24 + 25.0x25 + 25.3x26 + 22.3x27 +
// 14x31 + 40.0x32 + 20.0x33 + 13.0x34 + 12.0x35 + 15.0x36 + 25.0x37 +
// 17x41 + 20.0x42 + 15.0x43 + 20.0x44 + 20.0x45 + 22.0x46 + 19.0x47 <= 90
// where,
// 0 <= x11
// 0 <= x12
// 0 <= x13
// 0 <= x14
// 0 <= x15
// 0 <= x16
// 0 <= x17
// 0 <= x21
// 0 <= x22
// 0 <= x23
// 0 <= x24
// 0 <= x25
// 0 <= x26
// 0 <= x27
// 0 <= x31
// 0 <= x32
// 0 <= x33
// 0 <= x34
// 0 <= x35
// 0 <= x36
// 0 <= x37
// 0 <= x41
// 0 <= x42
// 0 <= x43
// 0 <= x44
// 0 <= x45
// 0 <= x46
// 0 <= x47
//
// offer = 8
// demand = 7

public class College {

    public static void main(String[] arg) {
        glp_prob lp;
        glp_smcp parm;
        SWIGTYPE_p_int ind;
        SWIGTYPE_p_double val;
        int ret;

        int numberVariables = 28;
        int numberConstraints = 16;
        String[] variables = {"x11", "x12", "x13", "x14", "x15", "x16", "x17",
                "x21", "x22", "x23", "x24", "x25", "x26", "x27",
                "x31", "x32", "x33", "x34", "x35", "x36", "x37",
                "x41", "x42", "x43", "x44", "x45", "x46", "x47"};
        double[][] constraints = {{-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,-1,-1,-1,-1},
                {1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0},
                {0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0},
                {0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0},
                {0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0},
                {0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0},
                {0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0},
                {0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1},
                {10,21.1,25,26.4,17.1,20,21.3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,20,17,13.5,14,25,25.3,22.3,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,14,40,20,13,12,15,25,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,17,20,15,20,20,22,19},
                {10,21.1,25,26.4,17.1,20,21.3,20,17,13.5,14,25,25.3,22.3,14,40,20,13,12,15,25,17,20,15,20,20,22,19}};
        double[] bounds = {-2,-2,-2,-2,1,1,1,1,1,1,1,33,33,33,33,90};
        double[] coefficients = {10,21.1,25,26.4,17.1,20,21.3,20,17,13.5,14,25,25.3,22.3,14,40,20,13,12,15,25,17,20,15,20,20,22,19};

        try {
            // Create problem
            lp = GLPK.glp_create_prob();
            System.out.println("Problem created");
            GLPK.glp_set_prob_name(lp, "Transportation 10 - College");

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
            GLPKUtil.set_lo_constraints(lp, ind, val, bounds,constraints, 1, 11, numberVariables);
            GLPKUtil.set_up_constraints(lp, ind, val, bounds,constraints, 12, numberConstraints, numberVariables);

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
            GLPK.glp_write_lp(lp, null, "transportation10.lp");

            // Solve model
            parm = new glp_smcp();
            GLPK.glp_init_smcp(parm);
            ret = GLPK.glp_simplex(lp, parm);

            // Retrieve solution
            if (ret == 0) {
                GLPKUtil.write_lp_solution(lp,"transportation10.sol");
                //GLPK.glp_write_sol(lp, "transportation1023.sol");
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

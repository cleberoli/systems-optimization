package transportation;

import util.GLPKUtil;
import org.gnu.glpk.*;

// Minimize z = 8x11 +  6x12 + 10x13 + 9x14 + 
//              9x21 + 12x22 + 13x23 + 7x24 + 
//             14x31 +  9x32 + 16x33 + 5x34
// subject to
// -x11 - x12 - x13 - x14 = -35
// -x21 - x22 - x23 - x24 = -50
// -x31 - x32 - x33 - x34 = -40
// x11 + x21 + x31 = 45
// x12 + x22 + x32 = 20
// x13 + x23 + x33 = 30
// x14 + x24 + x34 = 30
// where,
// 0 <= x11
// 0 <= x12
// 0 <= x13
// 0 <= x14
// 0 <= x21
// 0 <= x22
// 0 <= x23
// 0 <= x24
// 0 <= x31
// 0 <= x32
// 0 <= x33
// 0 <= x34
//
// offer = 125
// demand = 125

public class PowerCo {
    
    public static void main(String[] arg) {
        glp_prob lp;
        glp_smcp parm;
        SWIGTYPE_p_int ind;
        SWIGTYPE_p_double val;
        int ret;

        int numberVariables = 12;
        int numberConstraints = 7;
        String[] variables = {"x11", "x12", "x13", "x14",
                "x21", "x22", "x23", "x24",
                "x31", "x32", "x33", "x34"};
        double[][] constraints = {{-1,-1,-1,-1,0,0,0,0,0,0,0,0},
                {0,0,0,0,-1,-1,-1,-1,0,0,0,0},
                {0,0,0,0,0,0,0,0,-1,-1,-1,-1},
                {1,0,0,0,1,0,0,0,1,0,0,0},
                {0,1,0,0,0,1,0,0,0,1,0,0},
                {0,0,1,0,0,0,1,0,0,0,1,0},
                {0,0,0,1,0,0,0,1,0,0,0,1}};
        double[] bounds = {-35,-50,-40,45,20,30,30};
        double[] coefficients = {8,6,10,9,9,12,13,7,14,9,16,5};

        try {
            // Create problem
            lp = GLPK.glp_create_prob();
            System.out.println("Problem created");
            GLPK.glp_set_prob_name(lp, "Transportation 5 - PowerCo");

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
            GLPK.glp_write_lp(lp, null, "transportation5.lp");

            // Solve model
            parm = new glp_smcp();
            GLPK.glp_init_smcp(parm);
            ret = GLPK.glp_simplex(lp, parm);

            // Retrieve solution
            if (ret == 0) {
                GLPKUtil.write_lp_solution(lp,"transportation5.sol");
                //GLPK.glp_write_sol(lp, "transportation5.sol");
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

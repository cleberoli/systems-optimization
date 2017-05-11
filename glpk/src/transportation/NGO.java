package transportation;

import util.GLPKUtil;
import org.gnu.glpk.*;

// Minimize z = 0.080x11 + 0.070x12 + 0.065x13 + 0.080x14 + 0.083x15 + 0.080x16 + 0.083x17 + 
//              0.075x21 + 0.070x22 + 0.067x23 + 0.060x24 + 0.060x25 + 0.060x26 + 0.060x27 + 
//              0.045x31 + 0.040x32 + 0.040x33 + 0.027x34 + 0.030x35 + 0.027x36 + 0.030x37 +
//              0.050x41 + 0.045x42 + 0.045x43 + 0.040x44 + 0.040x45 + 0.040x46 + 0.045x47 +
//              0.060x51 + 0.055x52 + 0.050x53 + 0.055x54 + 0.055x55 + 0.055x56 + 0.060x57
// subject to
// -x11 - x12 - x13 - x14 - x15 - x16 - x17 >= -25000
// -x21 - x22 - x23 - x24 - x25 - x26 - x27 >= -23000
// -x31 - x32 - x33 - x34 - x35 - x36 - x37 >= -15000
// -x41 - x42 - x43 - x44 - x45 - x46 - x47 >= -22000
// -x51 - x52 - x53 - x54 - x55 - x56 - x57 >= -20000
// x11 + x21 + x31 + x41 + x51 >= 5000
// x12 + x22 + x32 + x42 + x52 >= 4000
// x13 + x23 + x33 + x43 + x53 >= 7000
// x14 + x24 + x34 + x44 + x54 >= 5000
// x15 + x25 + x35 + x45 + x55 >= 4000
// x16 + x26 + x36 + x46 + x56 >= 3500
// x17 + x27 + x37 + x47 + x57 >= 6000
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
// 0 <= x51
// 0 <= x52
// 0 <= x53
// 0 <= x54
// 0 <= x55
// 0 <= x56
// 0 <= x57
//
// offer = 105000
// demand = 34500

public class NGO {
    
    public static void main(String[] arg) {
        glp_prob lp;
        glp_smcp parm;
        SWIGTYPE_p_int ind;
        SWIGTYPE_p_double val;
        int ret;

        int numberVariables = 35;
        int numberConstraints = 12;
        String[] variables = {"x11", "x12", "x13", "x14", "x15", "x16", "x17",
                "x21", "x22", "x23", "x24", "x25", "x26", "x27",
                "x31", "x32", "x33", "x34", "x35", "x36", "x37",
                "x41", "x42", "x43", "x44", "x45", "x46", "x47",
                "x41", "x52", "x53", "x54", "x55", "x56", "x57"};
        double[][] constraints = {{-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,-1,-1,-1,-1},
                {1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0},
                {0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0},
                {0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0},
                {0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0},
                {0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0},
                {0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0},
                {0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1}};
        double[] bounds = {-25000,-23000,-15000,-22000,-20000,5000,4000,7000,5000,4000,3500,6000};
        double[] coefficients = {0.080, 0.070, 0.065, 0.080, 0.083, 0.080, 0.083,
                0.075, 0.070, 0.067, 0.060, 0.060, 0.060, 0.060,
                0.045, 0.040, 0.040, 0.027, 0.030, 0.027, 0.030,
                0.050, 0.045, 0.045, 0.040, 0.040, 0.040, 0.045,
                0.060, 0.055, 0.050, 0.055, 0.055, 0.055, 0.060};

        try {
            // Create problem
            lp = GLPK.glp_create_prob();
            System.out.println("Problem created");
            GLPK.glp_set_prob_name(lp, "Transportation 4 - NGO");

            // Define columns
            GLPK.glp_add_cols(lp, numberVariables);

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
            GLPK.glp_write_lp(lp, null, "transportation4.lp");
            System.out.println("aqui");

            // Solve model
            parm = new glp_smcp();
            GLPK.glp_init_smcp(parm);
            ret = GLPK.glp_simplex(lp, parm);

            // Retrieve solution
            if (ret == 0) {
                GLPKUtil.write_lp_solution(lp,"transportation4.sol");
                //GLPK.glp_write_sol(lp, "transportation4.sol");
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

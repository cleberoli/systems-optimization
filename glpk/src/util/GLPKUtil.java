package util;

import org.gnu.glpk.*;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class GLPKUtil {

    public static void write_lp_solution(glp_prob lp, String fileName) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            bw.write(GLPK.glp_get_prob_name(lp));
            bw.newLine();
            bw.write(GLPK.glp_get_obj_name(lp) + " = " + GLPK.glp_get_obj_val(lp));
            bw.newLine();

            for (int i = 1; i <= GLPK.glp_get_num_cols(lp); i++) {
                bw.write(GLPK.glp_get_col_name(lp, i) + " = " + GLPK.glp_get_col_prim(lp, i));
                bw.newLine();
            }

            bw.flush();
            bw.close();
        } catch(Exception e) {}

        System.out.println(GLPK.glp_get_prob_name(lp));
        System.out.println(GLPK.glp_get_obj_name(lp) + " = " + GLPK.glp_get_obj_val(lp));
        for (int i = 1; i <= GLPK.glp_get_num_cols(lp); i++) {
            System.out.println(GLPK.glp_get_col_name(lp, i) + " = " + GLPK.glp_get_col_prim(lp, i));
        }
    }

    public static void set_fx_constraints(glp_prob lp, SWIGTYPE_p_int ind, SWIGTYPE_p_double val, double[] bounds, double[][] constraints, int firstConstraint, int lastConstraint, int numberVariables) {
        for (int i = firstConstraint; i <= lastConstraint ; i++) {
            GLPK.glp_set_row_name(lp, i, "c" + i);
            GLPK.glp_set_row_bnds(lp, i, GLPKConstants.GLP_FX, bounds[i-1], bounds[i-1]);

            for (int j = 1; j <= numberVariables; j++) {
                GLPK.intArray_setitem(ind, j, j);
            }

            for (int j = 1; j <= numberVariables; j++) {
                GLPK.doubleArray_setitem(val, j, constraints[i-1][j-1]);
            }

            GLPK.glp_set_mat_row(lp, i, numberVariables, ind, val);
        }
    }

    public static void set_up_constraints(glp_prob lp, SWIGTYPE_p_int ind, SWIGTYPE_p_double val, double[] bounds, double[][] constraints, int firstConstraint, int lastConstraint, int numberVariables) {
        for (int i = firstConstraint; i <= lastConstraint ; i++) {
            GLPK.glp_set_row_name(lp, i, "c" + i);
            GLPK.glp_set_row_bnds(lp, i, GLPKConstants.GLP_UP, 0, bounds[i-1]);

            for (int j = 1; j <= numberVariables; j++) {
                GLPK.intArray_setitem(ind, j, j);
            }

            for (int j = 1; j <= numberVariables; j++) {
                GLPK.doubleArray_setitem(val, j, constraints[i-1][j-1]);
            }

            GLPK.glp_set_mat_row(lp, i, numberVariables, ind, val);
        }
    }

    public static void set_lo_constraints(glp_prob lp, SWIGTYPE_p_int ind, SWIGTYPE_p_double val, double[] bounds, double[][] constraints, int firstConstraint, int lastConstraint, int numberVariables) {
        for (int i = firstConstraint; i <= lastConstraint ; i++) {
            GLPK.glp_set_row_name(lp, i, "c" + i);
            GLPK.glp_set_row_bnds(lp, i, GLPKConstants.GLP_LO, bounds[i-1], 0);

            for (int j = 1; j <= numberVariables; j++) {
                GLPK.intArray_setitem(ind, j, j);
            }

            for (int j = 1; j <= numberVariables; j++) {
                GLPK.doubleArray_setitem(val, j, constraints[i-1][j-1]);
            }

            GLPK.glp_set_mat_row(lp, i, numberVariables, ind, val);
        }
    }
}

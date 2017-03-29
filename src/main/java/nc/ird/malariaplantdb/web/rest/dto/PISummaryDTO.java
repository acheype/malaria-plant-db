package nc.ird.malariaplantdb.web.rest.dto;

import java.math.BigDecimal;

/**
 * Plant Ingredient summary used to display the publications list
 *
 * @author acheype
 */
public class PISummaryDTO {

    public enum TestType {
        IN_VIVO, IN_VITRO
    }

    public final static String PETERS_TXT = "peters";

    public final static String RANE_TXT = "rane test";

    public final static String REPOSITORY_TXT = "repository";

    public final static String LIVER_STAGE_TXT = "liver stage";

    public final static String GAMETOCYTE_TXT = "gametocyte";

    public final static String OOCYST_TXT = "oocyst";

    public final static String SPOROZOITE_TXT = "sporozoite";

    public final static String TOXICITY_TXT = "toxicity";

    public final static String ERYTHROCYT_TXT = "erythrocyt";

    private boolean isInVivo = false;

    private boolean isInVivoPeters = false;

    private BigDecimal inVivoInhibition;

    private BigDecimal inVivoEd50;

    private boolean isInVivoRaneTest = false;

    private boolean isInVivoRepository = false;

    private boolean isInVivoLiverStage = false;

    private boolean isInVivoGametocyte = false;

    private boolean isInVivoOocyst = false;

    private boolean isInVivoSporozoite = false;

    private boolean isInVivoToxicity = false;

    private boolean isInVitro = false;

    private boolean isInVitroErythrocyt = false;

    private BigDecimal inVitroInhibition;

    private BigDecimal inVitroIc50;

    private BigDecimal inVitroMolIc50;

    private boolean isInVitroLiverStage = false;

    private boolean isInVitroGametocyte = false;

    private boolean isInVitroSporozoite = false;

    private boolean isInVitroToxicity = false;

    public void setTestedEntity(TestType testType, String screeningTest, BigDecimal inVivoInhibition, BigDecimal
        inVivoEd50, BigDecimal inVitroInhibition, BigDecimal inVitroIC50, BigDecimal inVitroMolIc50){
        if (TestType.IN_VIVO.equals(testType))
            isInVivo = true;
        else
            isInVitro = true;

        String screeningTestNormalized = screeningTest.replaceAll("^ +| +$|( )+", "$1").toLowerCase();
        switch (screeningTestNormalized){
            case PETERS_TXT :
                if (TestType.IN_VIVO.equals(testType)){
                    isInVivoPeters = true;
                    this.inVivoInhibition = inVivoInhibition;
                    this.inVivoEd50 = inVivoEd50;
                }
                break;
            case RANE_TXT :
                if (TestType.IN_VIVO.equals(testType))
                    isInVivoRaneTest = true;
                break;
            case REPOSITORY_TXT :
                if (TestType.IN_VIVO.equals(testType))
                    isInVivoRepository = true;
                break;
            case LIVER_STAGE_TXT :
                if (TestType.IN_VIVO.equals(testType))
                    isInVivoLiverStage = true;
                else
                    isInVitroLiverStage = true;
                break;
            case GAMETOCYTE_TXT :
                if (TestType.IN_VIVO.equals(testType))
                    isInVivoGametocyte = true;
                else
                    isInVitroGametocyte = true;
                break;
            case OOCYST_TXT :
                if (TestType.IN_VIVO.equals(testType))
                    isInVivoOocyst = true;
                break;
            case SPOROZOITE_TXT :
                if (TestType.IN_VIVO.equals(testType))
                    isInVivoSporozoite = true;
                else
                    isInVitroSporozoite = true;
                break;
            case TOXICITY_TXT :
                if (TestType.IN_VIVO.equals(testType))
                    isInVivoToxicity = true;
                else
                    isInVitroToxicity = true;
                break;
            case ERYTHROCYT_TXT :
                if (TestType.IN_VITRO.equals(testType)) {
                    isInVitroErythrocyt = true;
                    this.inVitroInhibition = inVitroInhibition;
                    this.inVitroIc50 = inVitroIC50;
                    this.inVitroMolIc50 = inVitroMolIc50;
                }
                break;
            default :
        }
    }

    public boolean isInVivo() {
        return isInVivo;
    }

    public void setIsInVivo(boolean isInVivo) {
        this.isInVivo = isInVivo;
    }

    public boolean isInVivoPeters() {
        return isInVivoPeters;
    }

    public void setIsInVivoPeters(boolean isInVivoPeters) {
        this.isInVivoPeters = isInVivoPeters;
    }

    public BigDecimal getInVivoInhibition() {
        return inVivoInhibition;
    }

    public void setInVivoInhibition(BigDecimal inVivoInhibition) {
        this.inVivoInhibition = inVivoInhibition;
    }

    public BigDecimal getInVivoEd50() {
        return inVivoEd50;
    }

    public void setInVivoEd50(BigDecimal inVivoEd50) {
        this.inVivoEd50 = inVivoEd50;
    }

    public boolean isInVivoRaneTest() {
        return isInVivoRaneTest;
    }

    public void setIsInVivoRaneTest(boolean isInVivoRaneTest) {
        this.isInVivoRaneTest = isInVivoRaneTest;
    }

    public boolean isInVivoRepository() {
        return isInVivoRepository;
    }

    public void setIsInVivoRepository(boolean isInVivoRepository) {
        this.isInVivoRepository = isInVivoRepository;
    }

    public boolean isInVivoLiverStage() {
        return isInVivoLiverStage;
    }

    public void setIsInVivoLiverStage(boolean isInVivoLiverStage) {
        this.isInVivoLiverStage = isInVivoLiverStage;
    }

    public boolean isInVivoGametocyte() {
        return isInVivoGametocyte;
    }

    public void setIsInVivoGametocyte(boolean isInVivoGametocyte) {
        this.isInVivoGametocyte = isInVivoGametocyte;
    }

    public boolean isInVivoOocyst() {
        return isInVivoOocyst;
    }

    public void setIsInVivoOocyst(boolean isInVivoOocyst) {
        this.isInVivoOocyst = isInVivoOocyst;
    }

    public boolean isInVivoSporozoite() {
        return isInVivoSporozoite;
    }

    public void setIsInVivoSporozoite(boolean isInVivoSporozoite) {
        this.isInVivoSporozoite = isInVivoSporozoite;
    }

    public boolean isInVivoToxicity() {
        return isInVivoToxicity;
    }

    public void setIsInVivoToxicity(boolean isInVivoToxicity) {
        this.isInVivoToxicity = isInVivoToxicity;
    }

    public boolean isInVitro() {
        return isInVitro;
    }

    public void setIsInVitro(boolean isInVitro) {
        this.isInVitro = isInVitro;
    }

    public boolean isInVitroErythrocyt() {
        return isInVitroErythrocyt;
    }

    public void setIsInVitroErythrocyt(boolean isInVitroErythrocyt) {
        this.isInVitroErythrocyt = isInVitroErythrocyt;
    }

    public BigDecimal getInVitroInhibition() {
        return inVitroInhibition;
    }

    public void setInVitroInhibition(BigDecimal inVitroInhibition) {
        this.inVitroInhibition = inVitroInhibition;
    }

    public BigDecimal getInVitroIc50() {
        return inVitroIc50;
    }

    public void setInVitroIc50(BigDecimal inVitroIc50) {
        this.inVitroIc50 = inVitroIc50;
    }

    public BigDecimal getInVitroMolIc50() {
        return inVitroMolIc50;
    }

    public void setInVitroMolIc50(BigDecimal inVitroMolIc50) {
        this.inVitroMolIc50 = inVitroMolIc50;
    }

    public boolean isInVitroLiverStage() {
        return isInVitroLiverStage;
    }

    public void setIsInVitroLiverStage(boolean isInVitroLiverStage) {
        this.isInVitroLiverStage = isInVitroLiverStage;
    }

    public boolean isInVitroGametocyte() {
        return isInVitroGametocyte;
    }

    public void setIsInVitroGametocyte(boolean isInVitroGametocyte) {
        this.isInVitroGametocyte = isInVitroGametocyte;
    }

    public boolean isInVitroSporozoite() {
        return isInVitroSporozoite;
    }

    public void setIsInVitroSporozoite(boolean isInVitroSporozoite) {
        this.isInVitroSporozoite = isInVitroSporozoite;
    }

    public boolean isInVitroToxicity() {
        return isInVitroToxicity;
    }

    public void setIsInVitroToxicity(boolean isInVitroToxicity) {
        this.isInVitroToxicity = isInVitroToxicity;
    }
}

package spacegame2.gamedata.drawableobject;

import javafx.beans.property.ReadOnlyDoubleProperty;

public interface Trajectory {
    ReadOnlyDoubleProperty posXProperty();
    ReadOnlyDoubleProperty posYProperty();
}
